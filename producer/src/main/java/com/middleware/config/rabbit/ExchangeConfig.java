package com.middleware.config.rabbit;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 6:10
 * @Description:
 * @version: 1.0
 */
@Configuration
//@Profile("pro")
public class ExchangeConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("con.delay.exchange");
    }
}
