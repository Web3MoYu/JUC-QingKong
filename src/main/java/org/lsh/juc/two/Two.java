package org.lsh.juc.two;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

public class Two {

    public static void main(String[] args) throws InterruptedException {
        /*
         LongAdder当有大量线程同时对adder进行修改时，他会用一个数组
          对于每一个线程，他们只有修改自己的数据，然后最后求和即可
         */
        LongAdder adder = new LongAdder();

        Runnable r = () -> {
            for (int i = 0; i < 100000; i++) {
                adder.add(1);
            }
        };
        for (int i = 0; i < 1000; i++) {
            new Thread(r).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(adder.sum());
    }

    @Test
    // 测试引用类型的原子性
    public void testRef() {
        String a = "Hello";
        String b = "World";
        AtomicReference<String> reference = new AtomicReference<>(a);
        reference.compareAndSet(a, b);
        System.out.println(reference.get());
    }
}
