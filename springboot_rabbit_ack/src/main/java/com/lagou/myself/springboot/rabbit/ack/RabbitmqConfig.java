package com.lagou.myself.springboot.rabbit.ack;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queue(){
        Queue queue = QueueBuilder.durable("queue.ackc").build();
        return queue;
    }

    @Bean
    public Exchange exchange(){
        Exchange exchange = ExchangeBuilder.directExchange("exchange.ackc").durable(true).build();
        return exchange;
    }

    @Bean
    public Binding binding(){
        Binding binding = new Binding("queue.ackc", Binding.DestinationType.QUEUE, "exchange.ackc", "key.ackc", null);
        return binding;
    }
}
