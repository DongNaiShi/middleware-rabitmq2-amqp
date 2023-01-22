package com.middleware.listener;

import com.middleware.config.PluginDelayConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther: dongns
 * @Date: 2023-01-23 4:25
 * @Description:
 * @version: 1.0
 */
@Slf4j
@Component
public class DelayListener {
    @RabbitListener(queues = {PluginDelayConfig.QUEUE})
    public void consumerDelay(Channel channel, Message message,String msg) throws IOException {
        log.info("延迟时间，进入消费,msg:{}",msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


}
