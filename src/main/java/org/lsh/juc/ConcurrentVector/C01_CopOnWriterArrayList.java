package org.lsh.juc.ConcurrentVector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class C01_CopOnWriterArrayList {

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new CopyOnWriteArrayList<>();  //这里使用CopyOnWriteArrayList来保证线程安全
        Runnable r = () -> {
            for (int i = 0; i < 100; i++)
                list.add("lbwnb");
        };
        for (int i = 0; i < 100; i++)
            new Thread(r).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(list.size());
    }
}
