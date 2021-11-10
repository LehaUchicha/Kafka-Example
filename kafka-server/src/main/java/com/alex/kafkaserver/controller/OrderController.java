package com.alex.kafkaserver.controller;

import com.alex.kafkaserver.dto.OrderDto;
import com.alex.kafkaserver.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/order")
    public void create() {
        OrderDto order = OrderDto.builder()
                .name("New Order #: " + System.nanoTime())
                .description("Standard description")
                .build();
        orderService.send(order);
    }
}
