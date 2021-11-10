package com.alex.kafkaserver.service.impl;

import com.alex.kafkaserver.dto.OrderDto;
import com.alex.kafkaserver.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<Long, OrderDto> kafkaOrderTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void send(OrderDto dto) {
        kafkaOrderTemplate.send("orders", dto);
    }

    @Override
    @KafkaListener(id = "OrderId", topics = {"orders"}, containerFactory = "singleFactory")
    public void consume(OrderDto dto) {
        log.info("-> consumed {}", writeValueAsString(dto));
    }

    private String writeValueAsString(OrderDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("Error happens during json processing", e);
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
