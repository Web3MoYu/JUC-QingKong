package org.lsh.juc.threadPool;

import java.util.concurrent.*;

public class C03_T3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
//        ScheduledFuture<String> schedule = service.schedule(() -> "hello world", 3, TimeUnit.SECONDS);
//        System.out.println(schedule.get());
        // 定时执行
//        service.scheduleAtFixedRate(
//                () -> System.out.println("hello world"), 3, 1, TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(
                () -> System.out.println("hello world"), 3, 1, TimeUnit.SECONDS);
    }
}
