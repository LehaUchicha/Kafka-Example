package com.alex.kafkaserver.service;

import com.alex.kafkaserver.dto.OrderDto;

public interface OrderService {

    void send(OrderDto dto);

    void consume(OrderDto dto);
}
