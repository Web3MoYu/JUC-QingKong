package org.lsh.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C02_T2 {

    public static void main(String[] args) {
        // 直接创建一个固定容量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 创建只有一个容量的线程池
        service = Executors.newSingleThreadExecutor();

        service.submit(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
            }
        });
    }
}
