package dubbo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端
 */
public class Server {
    public static final String ZOOKEEPER_NODE_KAIKEBA="/solomon";
    private Map<String,Class> serverMap = new HashMap<>();
    private ServerSocket serverSocket = null;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String serverName;

    public void start(String address,int port,String serverName) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.setReceiveBufferSize(1024*10);
//        serverSocket.setSoTimeout(1000*20);
        serverSocket.bind(new InetSocketAddress(address,port));
        Socket accept = null;
        System.out.println("服务已经开启");
        while((accept = serverSocket.accept())!=null){
            Socket finalAccept = accept;
            //开启多线程
            executorService.execute(() -> {
                ObjectInputStream objectInputStream = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    //输入流
                    objectInputStream = new ObjectInputStream(finalAccept.getInputStream());
                    //输出流
                    objectOutputStream = new ObjectOutputStream(finalAccept.getOutputStream());
                    while(true) {
                        if(Thread.currentThread().isInterrupted()){
                            break;
                        }
                        //类名
                        String className = objectInputStream.readUTF();
                        //方法名
                        String methodName = objectInputStream.readUTF();
                        //方法参数类型数组
                        Class[] params = (Class[]) objectInputStream.readObject();
                        //方法参数值
                        Object[] values = (Object[]) objectInputStream.readObject();
                        //读取客户端传参
                        Map<String,String> attachments = (Map<String, String>) objectInputStream.readObject();
                        KaikebaRpcContext.getContext().setAttachments(attachments);

                        for (int i = 0; i < values.length; i++) {
                            //判断为回调函数
                            if (values[i] instanceof String && ((String) values[i]).equals(params[i] + ".callback." + i)) {
                                values[i] = ProxyBean.getBean(params[i], objectInputStream, objectOutputStream, i);
                            }
                        }

                        //实现类对象
                        Class implementClass = serverMap.get(className);
                        Object object = implementClass.newInstance();
                        //获得实现类的方法对象
                        Method method = implementClass.getMethod(methodName, params);
                        //获得结果
                        Object result = method.invoke(object, values);
                        //结果标记
                        objectOutputStream.writeInt(0);
                        //输出结果
                        objectOutputStream.writeObject(result);
                        //输出传参
                        objectOutputStream.writeObject(KaikebaRpcContext.getServerContext().getAttachments());
                        objectOutputStream.flush();
                        //清楚上下文
                        KaikebaRpcContext.getContext().removeContext();
                        KaikebaRpcContext.getServerContext().removeServerContext();
                    }
                    //关闭输入流
                    objectInputStream.close();
                    //关闭输出流
                    objectOutputStream.close();
                } catch (Exception e) {
                    System.out.println("客户端已经关闭");
                } finally {
                    //关闭输入流
                    try {
                        if(objectInputStream!=null)objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //关闭输出流
                    try {
                        if(objectOutputStream!=null)objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public void register(String className,Class implementClass){
        serverMap.put(className,implementClass);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.5.2:2181",1000*3, event -> {
            if (event.getState()== Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        /**
         * 跟节点不存在
         */
        if(zooKeeper.exists(Server.ZOOKEEPER_NODE_KAIKEBA,false)==null){
            zooKeeper.create(Server.ZOOKEEPER_NODE_KAIKEBA,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        String serverNodeNameTmp = "kaikeba1";
        String sreverNodeAddress = "localhost:8989:weight=50";
        //向ZOOKEEPER注册服务
        String nodepath = Server.ZOOKEEPER_NODE_KAIKEBA+"/"+serverNodeNameTmp;
        if(zooKeeper.exists(nodepath,false)!=null){
            zooKeeper.delete(nodepath,-1);
        }
        zooKeeper.create(nodepath,sreverNodeAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Server server = new Server();
                //注册服务
                server.register(IOrder.class.getName(),IOrderImpl.class);
                //开启服务
                server.start("localhost",8989,serverNodeNameTmp);

    }
}