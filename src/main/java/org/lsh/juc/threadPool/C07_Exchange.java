package org.lsh.juc.threadPool;

import java.util.concurrent.Exchanger;

public class C07_Exchange {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                System.out.println("收到主线程传递的交换数据：" + exchanger.exchange("AAAA"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("收到子线程传递的交换数据：" + exchanger.exchange("BBBB"));
    }
}
