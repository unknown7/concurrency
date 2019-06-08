package sharingConstrainedResources.simulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class BankTellerSimulation {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(100);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, customers));
    }
}

class Customer {
    private final int servedTime;

    public int getServedTime() {
        return servedTime;
    }

    public Customer(int servedTime) {
        this.servedTime = servedTime;
    }

    @Override
    public String toString() {
        return "[" + servedTime + "]";
    }
}

class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Customer customer : this) {
            builder.append(customer);
        }
        return builder.toString();
    }
}

class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private Random random = new Random(47);

    public CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                TimeUnit.SECONDS.sleep(1);
                customers.put(new Customer(random.nextInt(1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int count = 0;
    private final int id = count++;
    private int customerServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public int compareTo(Teller that) {
        return this.customerServed < that.customerServed ? -1 : (this.customerServed == that.customerServed ? 0 : 1);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServedTime());
                synchronized (this) {
                    customerServed++;
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void doSomethingElse() {
        customerServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine : "Already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller: " + id;
    }
}

class TellerManager implements Runnable {
    private ExecutorService exec;
    private CustomerLine customers;
    private PriorityBlockingQueue<Teller> workingTeller = new PriorityBlockingQueue<>();
    private Queue<Teller> tellersDoOtherThings = new LinkedList<>();

    public TellerManager(ExecutorService exec, CustomerLine customers) {
        this.exec = exec;
        this.customers = customers;
        Teller teller = new Teller(customers);
        this.exec.execute(teller);
        workingTeller.add(teller);
    }

    public void adjustTellerNumber() {
        if (customers.size() / workingTeller.size() > 2) {
            if (tellersDoOtherThings.size() > 0) {
                Teller teller = tellersDoOtherThings.remove();
                teller.serveCustomerLine();
                workingTeller.add(teller);
                return;
            }
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTeller.add(teller);
        }
        if (customers.size() / workingTeller.size() < 2) {
            Teller teller = workingTeller.poll();
            teller.doSomethingElse();
            tellersDoOtherThings.add(teller);
        }
        if (customers.size() == 0) {
            while (workingTeller.size() > 0) {
                Teller teller = workingTeller.poll();
                teller.doSomethingElse();
                tellersDoOtherThings.add(teller);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(1);
                adjustTellerNumber();
                System.err.print(customers + "{");
                for (Teller teller : workingTeller) {
                    System.err.print(teller);
                }
                System.err.println("}");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(this + "terminating");
    }

    @Override
    public String toString() {
        return "TellerManager ";
    }
}









