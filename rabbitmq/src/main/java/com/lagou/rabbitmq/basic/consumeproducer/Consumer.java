package com.lagou.rabbitmq.basic.consumeproducer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Kouzhao> blockingQueue;

    public Consumer(BlockingQueue<Kouzhao> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }



    public void run() {

        while (true) {
            try {
                if (blockingQueue.remainingCapacity() <= 0) {
                    System.out.println("仓库剩余的数量不足，请加大生产");
                } else {
                    Thread.sleep(3000);
                    Kouzhao remove = blockingQueue.remove();
                    System.out.println("我消费了口罩：" + remove.toString());
                }
            } catch (Exception e) {

            }
        }
    }
}
