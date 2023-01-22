package com.middleware.listener;

import com.middleware.service.ProducerService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 6:33
 * @Description:
 * @version: 1.0
 */
@Component
@Slf4j
public class ConsumerListener {


    @Autowired
    private ProducerService producerService;

    private final int RETRY_MAX_ATTEMPTS=3;
    private final int RETRY_INTERVAL_IN_MILLISECOND=3000;

    @RabbitListener(bindings = @QueueBinding(
            value =@Queue(value = "con.queue",durable = "true",arguments = {@Argument(
                    name = "x-dead-letter-exchange",
                    value = "dead.letter.exchange"
            ),@Argument(name = "x-dead-letter-routing-key",value = "dead.letter")
            }),
            exchange = @Exchange(value = "pro.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"pro.business"}

    ))
    public void consumer(Channel channel,String id, Message message) throws Exception
    {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        int retryAttempts=0;//重试次数
        boolean consumerSuccess=false;
        while (!consumerSuccess&&retryAttempts<RETRY_MAX_ATTEMPTS)
        {
            try {
                producerService.business(id);
                consumerSuccess=true;
            }catch (Exception e)
            {
                log.error("消费失败开始重试!");
                log.error("异常消息:",e.getMessage());
                retryAttempts++;
                Thread.sleep(RETRY_INTERVAL_IN_MILLISECOND);
            }

        }
        if(consumerSuccess)
        {
            channel.basicAck(deliveryTag,false);
        }else {
            channel.basicNack(deliveryTag,false,true);
        }

    }

    @RabbitListener(queues = {"dead.letter.queue"})
    public void deadLetter(String id)
    {
        log.info("进入死信队列监听,id:{}",id);
    }

}
