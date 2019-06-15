//package dubbo;
//
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
///**
// * 客户端
// */
//public class Client {
//
//    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        ZooKeeper zooKeeper = new ZooKeeper("192.168.5.2:2181",30*1000, event -> {
//            if (event.getState()== Watcher.Event.KeeperState.SyncConnected) {
//                countDownLatch.countDown();
//            }
//        });
//        countDownLatch.await();
//        List<String> children = zooKeeper.getChildren(Server.ZOOKEEPER_NODE_KAIKEBA, false);
//        int i =5;
//        //获得所有的临时子节点
//        for (String child : children) {
//            //获得子节点上上的统计数据
//            Stat stat = new Stat();
//            //获得节点数据
//            byte[] data = zooKeeper.getData(Server.ZOOKEEPER_NODE_KAIKEBA + "/" + child, false, stat);
//            //字节转字符串
//            String value = new String(data);
//            //分割内容
//            String[] split = value.split(":");
//            //远程调用服务RPC
//            IOrder orderImplement = ProxyBean.getBean(IOrder.class,split[0],Integer.parseInt(split[1]));
//            KaikebaRpcContext.getContext().setAttachment("user","zhangsan");
//            KaikebaRpcContext.getContext().setAttachment("password","123456");
////            //调用返回结果
//            System.out.println(orderImplement.selectById(i++));
////            //异步回调
//            KaikebaRpcContext.getContext().setAttachment("user","zhangsan");
//            KaikebaRpcContext.getContext().setAttachment("password","123456");
//            orderImplement.selectById(i,order -> {
//                System.out.println(order);
//                return "客户端返回的值";
//            });
//        }
//    }
//}