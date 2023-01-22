package com.middleware.listener;

import com.middleware.service.ProducerService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 6:33
 * @Description:
 * @version: 1.0
 */
@Component
@Slf4j
public class ConsumerListener {


    private final int RETRY_MAX_ATTEMPTS = 3;
    private final int RETRY_INTERVAL_IN_MILLISECOND = 3000;
    @Autowired
    private ProducerService producerService;
    private Map<String, Object> map = new HashMap();

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "con.queue", durable = "true", arguments =
                    {@Argument(name = "x-dead-letter-exchange", value = "dead.letter.exchange")
                            , @Argument(name = "x-dead-letter-routing-key", value = "dead.letter.key")
                            , @Argument(name = "x-message-ttl", value = "60000", type = "java.lang.Integer")
                            //队列中消息过期时间为60s 队列中所有消息变为死信
                    }),
            exchange = @Exchange(value = "pro.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"pro.business"}

    ))
    public void consumer(Channel channel, Message message) throws Exception {

        String messageId = message.getMessageProperties().getMessageId();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        if (map.get(messageId) != null) {
            channel.basicAck(deliveryTag, false);
            return;
        }
        byte[] body = message.getBody();
        String bodyStr = new String(body);
        int retryAttempts = 0;//重试次数
        boolean consumerSuccess = false;
        while (!consumerSuccess && retryAttempts < RETRY_MAX_ATTEMPTS) {
            try {
                producerService.business(bodyStr);
                consumerSuccess = true;
                map.put(messageId, 1);
            } catch (Exception e) {
                log.error("消费失败开始重试!");
                log.error("异常消息:", e.getMessage());
                retryAttempts++;
                Thread.sleep(RETRY_INTERVAL_IN_MILLISECOND);
            }

        }
        if (consumerSuccess)//消费成功或者以及消费过了
        {
            channel.basicAck(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, true);
        }

    }

    @RabbitListener(queues = {"dead.letter.queue"})
    public void deadLetter(Channel channel, Message message, String id) throws Exception {
        log.info("进入死信队列监听,等待3s后删除,id:{}", id);
        Thread.sleep(3000);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);//死信队列拒绝后直接丢弃
    }

    @RabbitListener(queues = {"delay.dead.letter.queue"})
    public void delayDeadLetter(Channel channel, Message message) throws IOException {
        log.info("延时任务到时间了，开始执行,当前时间:{}", new Date());
        byte[] body = message.getBody();
        String data = new String(body);
        log.info("data:{}", data);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //执行完确认消息
    }


}
