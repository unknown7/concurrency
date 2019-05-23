package sharingConstrainedResources.lock;

import java.util.concurrent.TimeUnit;

/**
 * join不会释放锁
 */
public class Join implements Runnable {
    private static final Object LOCK = new Object();
    private final int ident;
    private final Node node;
    public Join(int ident, Node node) {
        this.ident = ident;
        this.node = node;
    }
    @Override
    public void run() {
        f();
    }
    private void f() {
        System.err.println("#" + ident + " try to get the lock");
        synchronized (LOCK) {
            System.err.println("#" + ident + " get the lock");
            try {
                Thread next = node.next.thread;
                next.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true)
                Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Node n1 = new Node();
        Node n2 = new Node();
        Join join1 = new Join(1, n1);
        Join join2 = new Join(2, n2);
        Thread thread1 = new Thread(join1);
        Thread thread2 = new Thread(join2);
        thread1.setName("1");
        thread2.setName("2");
        n1.thread = thread1;
        n2.thread = thread2;
        n1.next = n2;
        n2.next = n1;
        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(3);
        System.exit(0);
    }
}
class Node {
    Thread thread;
    Node next;
}
