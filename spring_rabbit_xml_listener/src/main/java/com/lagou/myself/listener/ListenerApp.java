package com.lagou.myself.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ListenerApp {

    public static void main(String[] args) {
     new ClassPathXmlApplicationContext("spring-rabbit.xml");
    }
}
