package com.middleware.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:05
 * @Description:
 * @version: 1.0
 */
@Service
@Slf4j
public class ProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void business(String id) {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("business process:{}", id);
    }

    public void businessByMQ(String id) {
        //构建消息
        Message message = MessageBuilder.withBody(id.getBytes()).build();
        //设置消息全局id
        message.getMessageProperties().setMessageId(UUID.randomUUID().toString());//设置自己的消息id
        //发送消息
        amqpTemplate.convertAndSend("pro.business", message);
    }

    public void businessByDelay(String id) {
        //构建消息
        Message message = MessageBuilder.withBody(id.getBytes()).build();
        //设置全局消息id
        message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
        //发送消息个消息
        Date dateTime = new Date();
        log.info("开始延时任务5s,当前时间:{}", dateTime);
        amqpTemplate.convertAndSend("con.delay.exchange", "con.delay.key", id, message1 -> {
            message1.getMessageProperties().setExpiration("5000");
            return message1;
        });
    }
    private final String PLUGIN_EXCHANGE="plugin.delay.exchange";
    private final String PLUGIN_ROUTE_KEY="plugin.delay.routeKey";
    public void sendMsg(String msg, Integer delay) {
        log.info("延迟:{}",delay);
        amqpTemplate.convertAndSend(PLUGIN_EXCHANGE, PLUGIN_ROUTE_KEY, msg, message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }
}
