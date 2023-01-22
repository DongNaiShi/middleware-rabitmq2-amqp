package com.middleware.config.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:20
 * @Description: rabbit配置类
 * @version: 1.0
 */
@Slf4j
@Configuration
@Profile("test")
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate.ConfirmCallback confirmCallback;

    @Autowired
    private RabbitTemplate.ReturnsCallback returnsCallback;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
        rabbitTemplate.setExchange("pro.exchange");
        return rabbitTemplate;
    }


}
