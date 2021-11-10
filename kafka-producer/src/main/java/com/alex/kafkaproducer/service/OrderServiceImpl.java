package com.alex.kafkaproducer.service;

import com.alex.kafkaproducer.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl {

    private final KafkaTemplate<Long, OrderDto> kafkaOrderTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderServiceImpl(KafkaTemplate<Long, OrderDto> kafkaOrderTemplate,
                            ObjectMapper objectMapper) {
        this.kafkaOrderTemplate = kafkaOrderTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void produce() {
        OrderDto dto = new OrderDto("New Order #: " + System.nanoTime(), "standard description");
        log.info("<- sending {}", writeValueAsString(dto));
        kafkaOrderTemplate.send("orders", dto);
    }

    private String writeValueAsString(OrderDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
