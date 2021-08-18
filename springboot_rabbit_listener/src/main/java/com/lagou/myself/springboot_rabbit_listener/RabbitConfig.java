package com.lagou.myself.springboot_rabbit_listener;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        Queue queue = QueueBuilder.durable("queue.boot").build();
        return queue;
    }
}
