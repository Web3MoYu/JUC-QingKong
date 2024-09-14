package org.lsh.juc.automicClass;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class One {

    // 原子类，能够保证原子性
    private static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            for (int j = 0; j < 1000; j++) {
                i.getAndIncrement();
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(i.get());
    }
}
