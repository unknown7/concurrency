package dubbo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 服务端
 */
public class Server2 {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.5.2:2181",1000*60, event -> {
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
        String serverNodeNameTmp = "kaikeba2";
        String sreverNodeAddress = "localhost:8999?weight=80";
        //向ZOOKEEPER注册服务
        zooKeeper.create(Server.ZOOKEEPER_NODE_KAIKEBA+"/"+serverNodeNameTmp,sreverNodeAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Server server = new Server();
                //注册服务
                server.register(IOrder.class.getName(),IOrderImpl2.class);
                //开启服务
                server.start("localhost",8999,serverNodeNameTmp);



    }
}