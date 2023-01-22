package com.middleware.config.rabbit.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:36
 * @Description:
 * @version: 1.0
 */
@Slf4j
@Component
public class ReturnsCallBackService implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("ReturnCallBack exchange:{},routingKey:{},replayCode:{},replyText:{},message:{}",
                returned.getExchange(),
                returned.getRoutingKey(),
                returned.getReplyCode(),
                returned.getReplyText(),
                returned.getMessage().toString()
        );

    }
}
