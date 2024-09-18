package org.lsh.juc.threadPool;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class C06_Semaphore {
    public static void main(String[] args) {
        //每一个Semaphore都会在一开始获得指定的许可证数数量，也就是许可证配额
        Semaphore semaphore = new Semaphore(2);   //许可证配额设定为2

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();   //申请一个许可证
                    System.out.println("许可证申请成功！");
                    TimeUnit.SECONDS.sleep(2);
                    semaphore.release();   //归还一个许可证
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
