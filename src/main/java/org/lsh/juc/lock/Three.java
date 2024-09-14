package org.lsh.juc.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Three {
    private static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        MyLock myLock = new MyLock();
        myLock.lock();
        Runnable r = () -> {
            for (int i = 0; i < 10; i++) {
                myLock.lock(); // 加锁
                a++;
                System.out.println(Thread.currentThread().getName() + ":" + a);
                myLock.unlock(); // 解锁，未使用lock时丢异常
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
        Thread.sleep(1000);
        myLock.unlock();
    }


    private static class MyLock implements Lock {

        private static class Sync extends AbstractQueuedSynchronizer {
            @Override
            protected boolean tryAcquire(int arg) {
                if (isHeldExclusively()) {
                    return true;
                }

                if (compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            @Override
            protected boolean tryRelease(int arg) {
                if (getState() == 0) {
                    throw new IllegalMonitorStateException();
                }
                if (isHeldExclusively()) {
                    setExclusiveOwnerThread(null);
                    setState(0);
                    return true;
                }
                return false;
            }

            @Override
            protected boolean isHeldExclusively() {
                return Thread.currentThread() == getExclusiveOwnerThread();
            }

            protected Condition newCondition() {
                return new ConditionObject();
            }
        }

        final Sync sync = new Sync();

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        @Override
        public boolean tryLock() {
            return sync.tryAcquire(1);
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(time));
        }

        @Override
        public void unlock() {
            sync.release(1);
        }

        @Override
        public Condition newCondition() {
            return sync.newCondition();
        }
    }
}
