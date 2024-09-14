package org.lsh.juc.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Two {

    @Test
    public void test1() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        new Thread(() -> {
            System.out.println("线程2想要获取锁");
            lock.lock();
            System.out.println("线程2成功获取锁");
        }).start();
        System.out.println("线程1持有锁：" + lock.getHoldCount());
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        System.out.println("线程1持有锁：" + lock.getHoldCount());
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        System.out.println("线程1持有锁：" + lock.getHoldCount());
    }

    @Test
    public void test2() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Thread t1 = new Thread(lock::lock), t2 = new Thread(lock::lock);
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("当前等待锁释放的线程数：" + lock.getQueueLength());
        System.out.println("线程1是否在等待队列中：" + lock.hasQueuedThread(t1));
        System.out.println("线程2是否在等待队列中：" + lock.hasQueuedThread(t2));
        System.out.println("当前线程是否在等待队列中：" + lock.hasQueuedThread(Thread.currentThread()));
    }

    @Test
    public void test3() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }).start();
        TimeUnit.SECONDS.sleep(1);
        lock.lock();
        System.out.println("当前Condition的等待线程数：" + lock.getWaitQueueLength(condition));
        condition.signal();
        System.out.println("当前Condition的等待线程数：" + lock.getWaitQueueLength(condition));
        lock.unlock();
    }

    @Test
    public void test4() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);
        Runnable action = () -> {
            System.out.println("线程 " + Thread.currentThread().getName() + " 开始获取锁...");
            lock.lock();
            System.out.println("线程 " + Thread.currentThread().getName() + " 成功获取锁！");
            lock.unlock();
        };
        for (int i = 1; i <= 10; i++) {   //建立10个线程
            new Thread(action, "T" + i).start();
        }
    }
}
