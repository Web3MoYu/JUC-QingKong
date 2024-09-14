package org.lsh.juc.ConcurrentVector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class C02_ConcurrentHashMap {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < 100; j++)
                    map.put(finalI * 100 + j, "lbwnb");
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(map.size());
    }
}
