package com.middleware.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: dongns
 * @Date: 2023-01-23 4:03
 * @Description: 基于插件实现延迟队列
 * @version: 1.0
 */
@Configuration
public class PluginDelayConfig {
    public static final String EXCHANGE="plugin.delay.exchange";
    public static final String QUEUE="plugin.delay.queue";
    public static final String ROUTE_KEY="plugin.delay.routeKey";

    @Bean
    public CustomExchange pluginExchange()
    {
        Map<String,Object>args=new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange(EXCHANGE,"x-delayed-message",true,false,args);
    }

    @Bean
    public Queue pluginQueue()
    {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding pluginBinding(@Qualifier("pluginQueue")Queue queue,@Qualifier("pluginExchange")CustomExchange customExchange)
    {
        return BindingBuilder.bind(queue).to(customExchange).with(ROUTE_KEY).noargs();
    }


}
