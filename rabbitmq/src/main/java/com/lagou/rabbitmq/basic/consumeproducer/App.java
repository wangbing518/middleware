package com.lagou.rabbitmq.basic.consumeproducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) {
        BlockingQueue<Kouzhao> blockingQueue=new ArrayBlockingQueue<Kouzhao>(20);
        Consumer consumer = new Consumer(blockingQueue);
        Producer producer=new Producer(blockingQueue);
        new Thread(consumer).start();
        new Thread(producer).start();
    }
}
