package com.middleware.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: dongns
 * @Date: 2023-01-22 16:52
 * @Description:
 * @version: 1.0
 */

@Configuration
public class RabbitConfig {
    @Bean
    public Exchange deadLetterExchange()
    {
        return new DirectExchange("dead.letter.exchange");
    }

    @Bean
    public Queue deadLetterQueue()
    {
        return new Queue("dead.letter.queue",true);
    }


    @Bean
    public Binding deadLetterBind()
    {
        return new Binding(
                "dead.letter.queue", Binding.DestinationType.QUEUE,
                "dead.letter.exchange","dead.letter",null
                );
    }



}

