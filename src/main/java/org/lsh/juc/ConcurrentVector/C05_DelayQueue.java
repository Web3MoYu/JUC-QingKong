package org.lsh.juc.ConcurrentVector;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class C05_DelayQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Test> queue = new DelayQueue<>();
        queue.add(new Test(1, 2, "2号"));   //1秒钟延时
        queue.add(new Test(3, 1, "1号"));   //1秒钟延时，优先级最高

        System.out.println(queue.take());    //注意出队顺序是依照优先级来的，即使一个元素已经可以出队了，依然需要等待优先级更高的元素到期
        System.out.println(queue.take());
    }

    private static class Test implements Delayed {
        private final long time;   //延迟时间，这里以毫秒为单位
        private final int priority;
        private final long startTime;
        private final String data;

        private Test(long time, int priority, String data) {
            this.time = TimeUnit.SECONDS.toMillis(time);   //秒转换为毫秒
            this.priority = priority;
            this.startTime = System.currentTimeMillis();   //这里我们以毫秒为单位
            this.data = data;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long leftTime = time - (System.currentTimeMillis() - startTime); //计算剩余时间 = 设定时间 - 已度过时间(= 当前时间 - 开始时间)
            return unit.convert(leftTime, TimeUnit.MILLISECONDS);   //注意进行单位转换，单位由队列指定（默认是纳秒单位）
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof Test)
                return priority - ((Test) o).priority;   //优先级越小越优先
            return 0;
        }

        @Override
        public String toString() {
            return data;
        }
    }
}

