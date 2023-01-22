package com.middleware.config.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:20
 * @Description: rabbit配置类
 * @version: 1.0
 */
@Slf4j
@Profile("dev")
@Component
public class RabbitConfig2 {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitTemplate.ConfirmCallback confirmCallback;

    private final RabbitTemplate.ReturnsCallback returnsCallback;

    public RabbitConfig2(RabbitTemplate rabbitTemplate, RabbitTemplate.ConfirmCallback confirmCallback, RabbitTemplate.ReturnsCallback returnsCallback) {
        this.rabbitTemplate = rabbitTemplate;
        this.confirmCallback = confirmCallback;
        this.returnsCallback = returnsCallback;
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
    }
}
