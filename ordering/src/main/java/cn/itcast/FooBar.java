package cn.itcast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class FooBar {


    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    //方法1：Semaphore
    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);

    public void printFoo1(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            s1.acquire();
            r.run();
            s2.release();
        }
    }

    public void printBar1(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            s2.acquire();
            r.run();
            s1.release();
        }
    }

    //方法2：使用wait();和notify();
    static Object obj = new Object();
    private volatile boolean wn = true;
    public void printFoo2(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            synchronized (obj) {
                if(!wn){
                    obj.wait();
                }
                r.run();
                wn = false;
                obj.notifyAll();
            }
        }
    }

    public void printBar2(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            synchronized (obj) {
                if(wn){
                    obj.wait();
                }
                r.run();
                wn = true;
                obj.notifyAll();
            }
        }
    }

    //方法3：使用 CyclicBarrier + boolean
    CyclicBarrier c1 = new CyclicBarrier(2);
    volatile boolean b = true;

    public void printFoo3(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            while (!b) ; //自旋
            r.run();
            b = false;
            c1.await();
        }
    }

    public void printBar3(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            c1.await();
            r.run();
            b = true;
        }
    }

    //方法4，阻塞队列
    private BlockingQueue queue1 = new LinkedBlockingQueue(1);
    private BlockingQueue queue2 = new LinkedBlockingQueue(1);

    public void printFoo4(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            queue1.put(i);
            r.run();
            queue2.put(i);
        }
    }

    public void printBar4(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            queue2.take();
            r.run();
            queue1.take();
        }
    }

    //方法5：Thread.yield();线程让步。 让当前线程从运行状态 转为 就绪状态
    private volatile boolean tb = true;

    public void printFoo(Runnable r) throws Exception {
        for (int i = 1; i <= n; ) { // 自旋，i没有加，一直循环
            if (tb) {
                r.run();
                i++;   //和 while(tb) ; 一样的效果
                tb = false;
            } else {
                Thread.yield();
            }
        }
    }

    public void printBar(Runnable r) throws Exception {
        for (int i = 1; i <= n; ) {
            if (!tb) {
                r.run();
                i++;
                tb = true;
            } else {
                Thread.yield();
            }
        }
    }

    //用3中方法：交替打印：FooBarFooBar…………
    public static void main(String[] args) throws Exception {

        FooBar o = new FooBar(5);
        Thread t1 = new Thread(() -> {
            try {
                o.printFoo3(() -> System.out.println("Foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                o.printBar3(() -> System.out.println("Bar"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

    }

}
