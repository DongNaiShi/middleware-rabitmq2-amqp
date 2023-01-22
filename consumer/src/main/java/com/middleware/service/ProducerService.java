package com.middleware.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:05
 * @Description:
 * @version: 1.0
 */
@Service
@Slf4j
public class ProducerService {
    private AtomicInteger integer = new AtomicInteger(1);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void business(String id) {
        log.info("当前count:{}", integer.get());
        if (integer.getAndIncrement() % 10 != 1) {
            //假设消息接收失败
            int i = 1 / 0;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("business process:{}", id);
    }

    public void businessByMQ(String id) {
        amqpTemplate.convertAndSend("pro.business", id);
    }
}
