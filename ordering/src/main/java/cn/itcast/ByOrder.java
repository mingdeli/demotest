package cn.itcast;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.*;
import java.util.function.IntConsumer;

interface MyRunnable extends Runnable {
    public MyRunnable setStr(String s);
}

public class ByOrder {

    private int n;

    public ByOrder(int n) {
        this.n = n;
    }

    Semaphore s1 = new Semaphore(0);
    Semaphore s2 = new Semaphore(0);
    Semaphore s3 = new Semaphore(0);

    //打印3的公约数
    public void printThree(Runnable r) throws Exception {
        for (int i = 1; i <= n; i++) {
            if(i%3 == 0 ) {
                s2.acquire();
                r.run();
                s1.release();
            }
        }
    }

    // 打印5
    public void printFive(Runnable r) throws Exception{
        for (int i = 1; i <= n; i++) {
            if(i%5==0) {
                s3.acquire();
                r.run();
                s1.release();
            }
        }
    }

    // 打印其他数字
    public void printOther(IntConsumer c) throws Exception{
        for (int i = 1; i <= n; i++) {

            if(i%3 == 0 ){
                s2.release();
                s1.acquire();
            }
            if(i%5==0){
                s3.release();
                s1.acquire();
            }
            if(i%3 != 0 && i%5 !=0 ) {
                c.accept(i);
            }
        }
    }


    //如 输入n=15，输出 1 2 three 4 five three 7 8 three 10 11 three 13 14 threefive
    public static void main(String[] args) throws Exception {
        ByOrder o = new ByOrder(16);
        Thread t1 = new Thread(() -> {
            try {
                o.printThree(() -> System.out.println("three"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                o.printFive(() -> System.out.println("Five"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                o.printOther((i) -> System.out.println(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
//
//
//        Semaphore s = new Semaphore(2);
//        s.acquire();
//        s.acquire(2);
//        s.release();
//        ExecutorService fix = Executors.newFixedThreadPool(5);
//        fix.execute(() -> System.out.println(Thread.currentThread().getName() + "334434444444"));
//        fix.submit(() -> System.out.println(Thread.currentThread().getName() + "fsdfeeeeee"));
//
////        printThree();
////        getUserInfo(1L);
//
//        //线程传参
//        MyRunnable myRunnable = new MyRunnable() {
//            String s;
//
//            @Override
//            public void run() {
//                System.out.println(s);
//            }
//
//            @Override
//            public MyRunnable setStr(String s) {
//                this.s = s;
//                return this;
//            }
//        };
//        new Thread(myRunnable.setStr("dsf")).start();
//
////        for (int i = 0; i < 15; i++) {
////            int finalI = i;
////            printThree(()->System.out.println(finalI));
////
////        }
//
//        MyCallable myCallable = new MyCallable();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<T> submit = executor.submit(myCallable);
//        T t = submit.get();
//        System.out.println(t);
//
//        Future<Object> nnn = executor.submit(() -> {
//            System.out.println("nnn");
//            return 142;
//        });
//        Object OOo = nnn.get();
//        System.out.println(OOo);
//
//
//    }
//
//
//    // 链接：https://www.zhihu.com/question/52801009/answer/2628267508
//
//
//    public static void getUserInfo(Long id) throws InterruptedException, ExecutionException {
//
//        ExecutorService executor = Executors.newFixedThreadPool(6);
//
//        CompletableFuture userFuture = CompletableFuture.supplyAsync(() -> {
////            getRemoteUserAndFill(id, userInfo);
//            return Boolean.TRUE;
//        }, executor);
//
//        CompletableFuture bonusFuture = CompletableFuture.supplyAsync(() -> {
////            getRemoteBonusAndFill(id, userInfo);
//            return Boolean.TRUE;
//        }, executor);
//
//        CompletableFuture growthFuture = CompletableFuture.supplyAsync(() -> {
////            getRemoteGrowthAndFill(id, userInfo);
//            return Boolean.TRUE;
//        }, executor);
//        CompletableFuture.allOf(userFuture, bonusFuture, growthFuture).join();
//
//        Object o2 = userFuture.get();
//        Object o1 = bonusFuture.get();
//        Object o = growthFuture.get();

    }

}

class MyCallable implements Callable<T> {

    @Override
    public T call() throws Exception {
        return null;
    }
}
