package com.middleware.config;

import lombok.Getter;
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
@Getter
public class RabbitDeadLetterConfig {

    private final String deadLetterExchange="dead.letter.exchange";
    private final String deadLetterQueue="dead.letter.queue";
    private final String deadLetterRouteKey="dead.letter.key";


    private final String delayPrefix="delay.";
    private final String delayDeadLetterQueue=delayPrefix+deadLetterQueue;
    private final String delayDeadLetterRouteKey=delayPrefix+deadLetterRouteKey;

    @Bean
    public Exchange deadLetterExchange()
    {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Queue deadLetterQueue()
    {
        return new Queue(deadLetterQueue,true);
    }

    @Bean
    public Binding deadLetterBind()
    {
        return new Binding(
                deadLetterQueue,Binding.DestinationType.QUEUE,
                deadLetterExchange,deadLetterRouteKey,null
                );
    }


    @Bean
    public Queue delayDeadLetterQueue()
    {
        return new Queue(delayDeadLetterQueue,true);
    }

    @Bean
    public Binding delayDeadLetterBind()
    {
        return new Binding(
                delayDeadLetterQueue, Binding.DestinationType.QUEUE,
                deadLetterExchange,delayDeadLetterRouteKey,null
        );
    }

}

