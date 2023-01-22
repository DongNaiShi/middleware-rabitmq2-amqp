package com.middleware.config.rabbit.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:35
 * @Description:
 * @version: 1.0
 */
@Slf4j
@Component
public class ConfirmCallBackService implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("ConfirmCallback ack:{},cause:{},data:{}", ack, cause, correlationData);
    }
}
