//package com.middleware.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
///**
// * @Auther: dongns
// * @Date: 2023-01-22 18:37
// * @Description:
// * @version: 1.0
// */
//@Slf4j
//@RabbitListener(queues = {"dead.letter.queue"})
//public class DeadLetterListener {
//    @RabbitHandler
//    public void listener()
//    {
//      log.info("进入死信队列监听");
//    }
//}
