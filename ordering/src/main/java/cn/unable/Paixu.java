package cn.unable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Paixu {
    public static void main(String[] args) {
//        Integer a[] = {29, 22, 60, 34, 69, 43, 83, 62, 0, 64, 90, 11, 50, 81, 36, 42, 1, 73, 52}; //12, 14, 30, 44, 25, 48, 81, 81, 87, 54,
        Integer b[] = new Integer[800_0000];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 800_0000);
        }
//        b = new Integer[]{2, 3, 5, 1, 4, 8};
        System.out.println(new SimpleDateFormat("hh-mm-ss.SSS").format(new Date()));
//        print(a);
//        shellpx(a);
//        print(a);
//        System.out.println("===========");
//        print(b);
//        shellpx(b);
//        moveshellpx(b);
//        quick(b);

        guiBin(b);
        System.out.println(new SimpleDateFormat("hh-mm-ss.SSS").format(new Date()));
//        print(b);
    }

    /**
     * 基数排序
     */
    public static void jishu(Integer[] b){

    }

    /**
     * 堆排序，大顶堆
     */
    public static void duiPx(Integer[] b){

    }
    /**
     * 归并排序
     */
    public static void guiBin(Integer[] b) {
        int[] aa = new int[b.length];
        guiBin(b, 0, b.length - 1, aa);
    }

    public static void guiBin(Integer[] a, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            guiBin(a, left, mid, temp);
            guiBin(a, mid + 1, right, temp);
            meger(a, left, mid, right, temp);
        }
    }

    public static void meger(Integer a[], int left, int mid, int right, int[] temp) {
        int L = left;
        int r = mid + 1;
        int t = 0;
        //两个数组 合并到 一个，谁小 谁在前
        while (L <= mid && r <= right) {
            if (a[L] <= a[r]) {
                temp[t] = a[L];
                t++;
                L++;
            } else {
                temp[t] = a[r];
                t++;
                r++;
            }
        }
        while (L <= mid) {
            temp[t] = a[L];
            t++;
            L++;
        }
        while (r <= right) {
            temp[t] = a[r];
            t++;
            r++;
        }
        //把数组 copy到 a[]
        t = 0;
        int teml = left;
        while (teml <= right) {
            a[teml] = temp[t];
            t++;
            teml++;
        }
    }

    /* 快排
     */
    public static void quick(Integer a[]) {
        quick(a, 0, a.length - 1);
    }

    public static void quick(Integer a[], int left, int right) {
        int t;
        int l = left;
        int r = right;
        int mid = a[(l + r) / 2];
        while (l < r) {
            //找到比mid大的值
            while (a[l] < mid)
                l++;
            while (a[r] > mid)
                r--;
            if (l >= r) {
                break;
            }
            t = a[l];
            a[l] = a[r];
            a[r] = t;
            if (a[l] == mid) {
                r--;
            }
            if (a[r] == mid)
                l++;
        }
        if (l == r) {
            l++;
            r--;
        }
        //向左递归
        if (left < r) {
            quick(a, left, r);
        }
        if (right > l) {
            quick(a, l, right);
        }
    }

    /**
     * shell排序 交换
     *
     * @param a
     */
    public static void shellpx(Integer a[]) {
        Integer temp = 0;
        //分组5组 8
        for (int gap = a.length / 2; gap > 0; gap = gap / 2) {
            //每一组遍历
            for (int j = gap; j < a.length; j++) {
                //一组内 每个值比较交换；
                for (int k = j - gap; k >= 0; k = k - gap) {
                    if (a[k] > a[gap + k]) {
                        temp = a[k];
                        a[k] = a[gap + k];
                        a[gap + k] = temp;
                    }
                }
            }
//            System.out.println("第" + gap + "轮");
//            print(a);
        }
    }

    /**
     * shell 排序 移动；效率高
     *
     * @param a
     */
    public static void moveshellpx(Integer a[]) {
        //分组5组
        for (int gap = a.length / 2; gap > 0; gap = gap / 2) {
            //每一组遍历
            for (int i = gap; i < a.length; i++) {
                //一组内 每个值比较交换；
                int j = i;
                int temp = a[j];
                if (a[j] < a[j - gap]) {
                    while (j - gap >= 0 && temp < a[j - gap]) {
                        a[j] = a[j - gap];
                        j = j - gap;
                    }
                    a[j] = temp;
                }
            }
        }
    }


    public static void print(Integer a[]) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println("");
        System.out.println("===================");
    }
}
