package com.myself.lagou.anno;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;


/**
 * @ClassName RabbitConfig
 * @Description
 * @Author wb
 * @Date 2021/8/18 0018 下午 3:38
 */
@Configuration
public class RabbitConfig {

    //链接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new CachingConnectionFactory(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        return factory;
    }
    // RabbitTemplate

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    //RabbitAdmin
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    //queue
    @Bean
    public Queue queue(){
        Queue queue = QueueBuilder.durable("queue.anono").build();
        return queue;
    }


    //Exchange
    @Bean
    public Exchange exchange(){
        Exchange exchange = ExchangeBuilder.directExchange("ex.anno.f").durable(true).build();
        return exchange;
    }

    //Binding
    @Bean
    @Autowired
    public Binding binding(Queue queue,Exchange exchange){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("key.anno").noargs();
        return binding;
    }
}
