package com.middleware.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        log.info("business process:{}",id);
    }

    public void businessByMQ(String id) {
        amqpTemplate.convertAndSend("pro.business",id);
    }
}
