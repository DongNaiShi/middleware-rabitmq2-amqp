package com.middleware.controller;

import com.middleware.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: dongns
 * @Date: 2023-01-22 5:05
 * @Description:
 * @version: 1.0
 */
@RestController
@RequestMapping("pro")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("business")
    public void business(@RequestParam String id)
    {
        producerService.business(id);
    }

    @GetMapping("businessByMQ")
    public void businessByMQ(@RequestParam String id)
    {
        producerService.businessByMQ(id);
    }
}
