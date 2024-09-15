package org.lsh.juc.ConcurrentVector;

import java.util.concurrent.PriorityBlockingQueue;

public class C04_PriorityBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Integer> queue =
                new PriorityBlockingQueue<>(10, Integer::compare);   //可以指定初始容量（可扩容）和优先级比较规则，这里我们使用升序
        queue.add(3);
        queue.add(1);
        queue.add(2);
        System.out.println(queue);    //注意保存顺序并不会按照优先级排列，所以可以看到结果并不是排序后的结果
        System.out.println(queue.poll());   //但是出队顺序一定是按照优先级进行的
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
