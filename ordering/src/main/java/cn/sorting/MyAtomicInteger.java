package cn.sorting;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyAtomicInteger {

    public static void main(String[] args) {

    }
}

class MyAtomicInt {
    private volatile int value;
    private static final long valueOffset;

    static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInt.class.getDeclaredField("value"));

        } catch (NoSuchFieldException e) {
            throw new RuntimeException();
        }
    }

    public int getValue(){
        return value;
    }

    public void decrement(int amout){
        while (true){
            int prev = this.value;
            int next = prev-amout;
            if(UNSAFE.compareAndSwapInt(this,valueOffset, prev, next)){
                break;
            }
        }
    }
}






class UnsafeAccessor {

    private static final Unsafe unsafe1;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe1 = (Unsafe) theUnsafe.get(null);
        } catch (IllegalAccessException  | NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error();
        }
    }
    public static Unsafe getUnsafe(){
        return unsafe1;
    }
}