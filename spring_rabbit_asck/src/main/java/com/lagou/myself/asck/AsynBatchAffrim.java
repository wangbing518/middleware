package com.lagou.myself.asck;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * 异步确认是否确定的等待机制
 * @ClassName AsynBatchAffrim
 * @Description
 * @Author wb
 * @Date 2021/8/19 0019 下午 5:22
 */
public class AsynBatchAffrim {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //向RabbitNQ服务器发送AMQP命令，将当前通道标记为发送方确认通道
        channel.confirmSelect();
        channel.queueDeclare("que.batch2",true,false,false,null);
        channel.exchangeDeclare("exchagen.batch2", BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueBind("que.batch2","exchagen.batch2","key.batch2");
        String message="hello2-";
        ConcurrentNavigableMap<Long,String> outstandingConfirms=new ConcurrentSkipListMap();
        //deliveryTag 是消息ID
        ConfirmCallback confirmCallback=(deliveryTag,multiple)->{
            if (multiple){
                System.out.println("消息编号小于等于"+deliveryTag+"的消息都已经被确认");
                ConcurrentNavigableMap<Long, String> headMap = outstandingConfirms.headMap(deliveryTag, true);
                //清空outstandingConfirms已经被确认的消息信息
                headMap.clear();
            }else {
                //移除已经被确认的消息
                outstandingConfirms.remove(deliveryTag);
                System.out.println("消息编号等于"+deliveryTag+"的消息都已经被确认");
            }
        };
        //设置channel监听器 ，处理确认的消息和不确认的消息
        channel.addConfirmListener(confirmCallback,(deliveryTag,multiple)->{
            if (multiple){
                System.out.println("消息编号小于等于"+deliveryTag+"的消息 不确认");

            }else {
                System.out.println("消息编号小于等于"+deliveryTag+"的消息  不确认");
            }
        });
        String mes="HELLO-";
        for (int i=1;i<1000;i++){
            //获取到下一条即将发送的消息的消息ID
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("exchagen.batch2","key.batch2",null,(mes+i).getBytes("utf-8"));
            System.out.println("编号为:"+nextPublishSeqNo+"的消息已经发送成功,尚未确认");
            outstandingConfirms.put(nextPublishSeqNo,(mes+i));
        }
        Thread.sleep(5000);
        channel.close();
        connection.close();
    }
}
