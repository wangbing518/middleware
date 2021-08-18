package com.myself.lagou.springboot_rabbit_producer;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        Queue queue = QueueBuilder.durable("queue.boot").build();
        return queue;
    }

    @Bean
    public Exchange exchange(){
        Exchange exchange = ExchangeBuilder.directExchange("ex.boot").durable(true).build();
        return exchange;
    }

    @Bean
    public Binding binding(){
        Binding binding = new Binding("queue.boot", Binding.DestinationType.QUEUE,
                "ex.boot", "key.boot", null);
        return binding;
    }
}
