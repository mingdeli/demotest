package cn.algorithm;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

// 3个人在 2个景点拍照，分别拍1，3，5张。顺序拍
public class OrderPrint135 {
    private int times = 2;

    public OrderPrint135(int times) {
        this.times = times;
    }

    public OrderPrint135() {
    }

    private volatile boolean b = false;
    CyclicBarrier c = new CyclicBarrier(2);
    CyclicBarrier c1 = new CyclicBarrier(2);

    private void printOne1(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i <times; i++) {
            while(b) Thread.yield();
            r.run();
            b = true;
            c.await();
        }
    }
    private void printThree1(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            c1.await();
            int k = 1;
            while( k++ <= 3){
                r.run();
            }
            b=false;
        }
    }
    private void printFive1(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            c.await();
            int k = 1;
            while( k++ <= 5){
                r.run();
            }
            c1.await();
        }
    }

    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);
    Semaphore s3 = new Semaphore(0);
    private void printOne2(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i <times; i++) {
            s1.acquire();
            r.run();
            s2.release();
        }
    }
    private void printThree2(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            s2.acquire();
            int k = 1;
            while( k++ <= 3){
                r.run();
            }
            s3.release();
        }
    }
    private void printFive2(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            s3.acquire();
            int k = 1;
            while( k++ <= 5){
                r.run();
            }
            s1.release();
        }
    }

    // wait notify
    final static Object obj = new Object();
    private void printOne(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i <times; i++) {
            synchronized (obj){
                r.run();
                obj.wait();
            }
        }
    }
    private void printThree(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            synchronized (obj) {
                int k = 1;
                while (k++ <= 3) {
                    r.run();
                }
            }
        }
    }
    private void printFive(Runnable r) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < times; i++) {
            s3.acquire();
            int k = 1;
            while( k++ <= 5){
                r.run();
            }
            s1.release();
        }
    }

    public static void main(String[] args) {
        OrderPrint135 o = new OrderPrint135();
        Thread t1 = new Thread(() -> {
            try {
                o.printThree(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("three ");});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                o.printFive(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Five ");});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                o.printOne(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("One ");});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }


}
