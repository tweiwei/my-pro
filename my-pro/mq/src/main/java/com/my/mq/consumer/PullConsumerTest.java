package com.my.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class PullConsumerTest {
    public static void main(String[] args){
        String mqInfo = args[0];
        String[] arrs = mqInfo.split(",");
        String nameserv = arrs[0];
        String topic = arrs[1];
        String tag = arrs[2];

        String group = args[1];
        int timeout = Integer.parseInt(args[2]);
        int times = Integer.parseInt(args[3]);

        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(group);
        consumer.setNamesrvAddr(nameserv);
        consumer.setInstanceName("ConsumerTest");
        try {
            consumer.subscribe(topic, tag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            consumer.start();

            for(int i=0; i<times; i++){
                List<MessageExt> list = consumer.poll(timeout);
                for(MessageExt messageExt : list){
                    System.out.println("message="+new String(messageExt.getBody()));
                    System.out.println("messageExt="+messageExt);
                }
            }

            System.out.println("begin to consumer...");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.shutdown();
    }
}
