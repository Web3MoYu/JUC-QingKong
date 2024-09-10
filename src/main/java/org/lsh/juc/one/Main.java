package org.lsh.juc.one;

import org.junit.jupiter.api.Test;

public class Main {
    private static volatile int a = 0;

    @Test
    public void test() throws InterruptedException {
        Runnable r = () -> {
            synchronized (Main.class) {
                for (int i = 0; i < 10000; i++) {
                    a++;
                }
            }
        };
        new Thread(r).start();
        new Thread(r).start();

        System.out.println(a);
    }
}
