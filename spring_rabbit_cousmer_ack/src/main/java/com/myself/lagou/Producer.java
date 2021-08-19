package com.myself.lagou;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue.ca", true, false, false, null);
        channel.exchangeDeclare("ex.ca", BuiltinExchangeType.DIRECT, true, false, null);
        channel.queueBind("queue.ca", "ex.ca", "key.ca");
        for (int i = 1; i <= 5; i++) {
            channel.basicPublish("ex.ca", "key.ca", null, ("hello-" + i).getBytes());
        }
        channel.close();
        connection.close();
    }
}
