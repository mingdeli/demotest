package com.ldm.testplugin;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    /**
     * 抢车位, 10 部汽车 1 个停车位
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        //停车位
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            Thread.sleep(500);
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() +"寻找车位");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"停车停好了");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"溜了溜了啦啦啦");
                    semaphore.release();
                }
            },"汽车"+i).start();
        }
    }
}
