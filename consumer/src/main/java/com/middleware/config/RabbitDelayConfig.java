package com.middleware.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: dongns
 * @Date: 2023-01-23 1:11
 * @Description: 基于死信实现延迟队列
 * @version: 1.0
 */
@Configuration
@Getter
public class RabbitDelayConfig {


    private final String queue = "con.delay.queue";
    private final String exchange = "con.delay.exchange";
    private final String routeKey = "con.delay.key";
    @Autowired
    private RabbitDeadLetterConfig deadLetterConfig;

    //延迟队列
    @Bean
    public Queue delayQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", deadLetterConfig.getDeadLetterExchange());
        arguments.put("x-dead-letter-routing-key", deadLetterConfig.getDelayDeadLetterRouteKey());
        arguments.put("x-message-ttl", 20000);
        return new Queue(queue, true, false, false, arguments);
    }

    //延迟交换机
    @Bean
    public TopicExchange delayTopicExchange() {
        return new TopicExchange(exchange, true, false);
    }

    //绑定
    @Bean
    public Binding delayBind() {
        return new Binding(queue, Binding.DestinationType.QUEUE, exchange, routeKey, null);
    }
}
