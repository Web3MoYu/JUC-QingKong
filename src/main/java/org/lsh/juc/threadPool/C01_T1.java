package org.lsh.juc.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class C01_T1 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 4,   //2个核心线程，最大线程数为4个
                        3, TimeUnit.SECONDS,        //最大空闲时间为3秒钟
                        new ArrayBlockingQueue<>(2));     //这里使用容量为2的ArrayBlockingQueue队列

        for (int i = 0; i < 6; i++) {   //开始6个任务
            int finalI = i;
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 开始执行！（" + finalI);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " 已结束！（" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);    //看看当前线程池中的线程数量
        System.out.println("线程池中线程数量：" + executor.getPoolSize());
        TimeUnit.SECONDS.sleep(5);     //等到超过空闲时间
        System.out.println("线程池中线程数量：" + executor.getPoolSize());

        /*
            使用完线程池记得关闭，不然程序不会结束，
            它会取消所有等待中的任务以及试图中断正在执行的任务，关闭后，无法再提交任务，一律拒绝
         */
        executor.shutdownNow();
        //executor.shutdown();     同样可以关闭，但是会执行完等待队列中的任务再关闭
    }
}
