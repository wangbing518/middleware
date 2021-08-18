package com.lagou.myself.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ListenerApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
    }
}
