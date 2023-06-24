package cn.sorting;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 计算1+2+3+……+n
 */
public class SumN {

    private static Integer sum = 0;
    private static Integer n = 10005;

    private static Integer cyclicSize = 10; //线程数量
    private static CyclicBarrier cyclicBarrier;
    private static Lock lock;

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(cyclicSize, new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "所有子线程相加完毕，准备加和 sum--> sum:" + sum);
            }
        });

        lock = new ReentrantLock();

        int avg = n / cyclicSize;
        int rem = n % cyclicSize;

        for (int i = 1; i <= cyclicSize; i++) {
            int left = (i - 1) * avg + 1;
            int right = i == cyclicSize ? (i * avg + rem) : (i * avg);

            new Thread(() -> {
                int res = 0;
                for (int j = left; j <= right; j++) {
                    res += j;
                }
                System.out.println(Thread.currentThread().getName() + " 等待其他线程 --> left:" + left + ", right:" + right + ", res:" + res);
                try {
                    cyclicBarrier.await(); //等待其他线程执行
                } catch (Exception e) {
                    e.printStackTrace();
                }

                lock.lock();
                try {
                    sum += res;
                    System.out.println(Thread.currentThread().getName() + " 添加至sum --> left:" + left + ", right:" + right + ", res:" + res + ", sum: " + sum);
                } finally {
                    lock.unlock();
                }
            }).start();
        }


    }
}
