package dubbo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProxyBean {
    public static <T> T getBean(Class interfaceClass,String address,int port) throws IOException {
        Socket socket  = new Socket(address,port);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois  = new ObjectInputStream(socket.getInputStream());
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},new InvokerInvocationHandler(interfaceClass,ois,oos));
    }
    public static <T> T getBean(Class interfaceClass,ObjectInputStream ois,ObjectOutputStream oos){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},new InvokerInvocationHandler(interfaceClass,ois,oos));
    }
    public static <T> T getBean(Class interfaceClass,ObjectInputStream ois,ObjectOutputStream oos,Integer paramIndex){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},new InvokerInvocationHandler(interfaceClass,ois,oos,paramIndex));
    }
    private static class InvokerInvocationHandler implements  InvocationHandler{
        protected ObjectOutputStream obp = null;
        protected ObjectInputStream ois = null;
        protected Class interfaceClass = null;
        protected Integer paramIndex = null;

        public InvokerInvocationHandler(Class interfaceClass,ObjectInputStream ois,ObjectOutputStream obp){
            this.obp = obp;
            this.ois = ois;
            this.interfaceClass = interfaceClass;
        }

        public InvokerInvocationHandler(Class interfaceClass,ObjectInputStream ois,ObjectOutputStream obp,Integer paramIndex){
            this.obp = obp;
            this.ois = ois;
            this.interfaceClass = interfaceClass;
            this.paramIndex = paramIndex;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().endsWith("toString")){
                return "";}
            if(paramIndex!=null){
                //标记为参数回调
                obp.writeInt(1);
                //发送回调函数为第几个参数
                obp.writeInt(paramIndex);
            }else {
                //发送接口类名
                obp.writeUTF(interfaceClass.getName());
            }
            //发送方法名
            obp.writeUTF(method.getName());
            //发送方法参数类型对象
            Class<?>[] parameterTypes = method.getParameterTypes();
            obp.writeObject(parameterTypes);
            //临时存储回调函数
            Map<Integer,Object> callbackMapper = new HashMap<Integer,Object>();
            //判断参数是否有回调对象
            for (int i = 0; i < args.length; i++) {
                Class<?> instanceClass = args[i].getClass();
                if (Arrays.stream(instanceClass.getInterfaces()).anyMatch(aClass -> aClass.isAssignableFrom(IOrder.BeanListener.class))) {
                    callbackMapper.put(i,args[i]);
                    args[i]=(parameterTypes[i]+".callback."+i);
                }
            }
            //发送参数值
            obp.writeObject(args);
            if(paramIndex==null) {
                //发送附件
                obp.writeObject(KaikebaRpcContext.getContext().getAttachments());
                //清理数据
                KaikebaRpcContext.removeContext();
                KaikebaRpcContext.removeServerContext();
            }
            if(paramIndex!=null){//服务端参数回调值要获得只,没有后续操作
                return  ois.readObject();
            }
            //读取标记
            //0:结果,1:回调
            int flag=0;
            Object result = null;
            do{
                flag = ois.readInt();
                if(flag==1){//读取客户端值
                    int callIndex = ois.readInt();//第几个回调参数
                    String callMethod = ois.readUTF();
                    Class[] params = (Class[]) ois.readObject();//参数类型
                    Object [] values = (Object[]) ois.readObject();//参数值
                    Object o = callbackMapper.get(callIndex);
                    Class<?> aClass = o.getClass();
                    obp.writeObject(aClass.getMethod(callMethod, params).invoke(o, values));
                }
                if(flag==0){
                    result = ois.readObject();
                    KaikebaRpcContext.getServerContext().setAttachments((Map<String, String>) ois.readObject());
                }
            }while (flag==1);
            return result;
        }
        @Override
        protected void finalize() throws Throwable {
            ois.close();
            obp.close();
        }
    }
}
