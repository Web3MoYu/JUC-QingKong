package org.lsh.juc.one;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class One {
    private static volatile int a = 0;

    @Test
    public void test01() throws InterruptedException {
        Runnable r = () -> {
            synchronized (One.class) {
                for (int i = 0; i < 10000; i++) {
                    a++;
                }
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        Thread.sleep(1000);
        System.out.println(a);
    }

    @Test
    public void test02() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable r = () -> {
            for (int i = 0; i < 100; i++) {
                lock.lock(); // 加锁
                a++;
                System.out.println(Thread.currentThread().getName() + ":" + a);
                lock.unlock(); // 解锁，未使用lock时丢异常
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        Thread.sleep(1000);
        System.out.println(a);
    }

    @Test
    public void test03() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            System.out.println("线程1进入等待状态");
            try {
                condition.await(); // await之后锁会释放掉
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("线程1等待结束");
            lock.unlock();
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            System.out.println("线程2开始唤醒其他等待线程");
            condition.signal();
            System.out.println("线程2结束");
            lock.unlock();
        }).start();
    }
}
