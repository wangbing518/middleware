package com.lagou.rabbitmq.basic.consumeproducer;


import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Kouzhao> blockingQueue;

    public Producer(BlockingQueue<Kouzhao> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void run() {
        int size = 0;
        while (true){
            try {
                if (blockingQueue.remainingCapacity()>20){
                    System.out.println("仓库已满，暂停生产");
                }else {
                    Thread.sleep(1000);
                    Kouzhao kouzhao=new Kouzhao("KN95",++size);
                    blockingQueue.put(kouzhao);
                    System.out.println("我在生产口罩，当前库存是：" +
                            blockingQueue.size());
                }
            }catch (Exception e){

            }
        }
    }
}
