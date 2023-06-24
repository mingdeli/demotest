package cn.sorting;

import java.util.concurrent.*;

public class MyExecutors {

    public static void main(String[] args) {

        ExecutorService woshi = Executors.newSingleThreadExecutor();
        ExecutorService w = Executors.newFixedThreadPool(2);
        ExecutorService t = Executors.newCachedThreadPool();
        for (int i = 0; i < 1; i++) {
            int finalI = i;
            woshi.submit(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "||| 你好 " + finalI);
            });
        }
        ThreadPoolExecutor dfd = new ThreadPoolExecutor(1, 1, 1, TimeUnit.NANOSECONDS,
                new LinkedBlockingQueue<>(1), (r, executor) -> {
            System.out.println("我要拒绝策略："+executor.getRejectedExecutionHandler());
        });
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            dfd.execute(() -> System.out.println("fdddddfffffffffffffff=    "+ finalI));
        }
        System.out.println("轰烈烈");
    }

}
