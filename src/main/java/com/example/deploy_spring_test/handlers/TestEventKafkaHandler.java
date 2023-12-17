package com.example.deploy_spring_test.handlers;

import org.springframework.stereotype.Component;

@Component
public class TestEventKafkaHandler extends BaseKafkaHandler<String> {
    @Override
    public void handlePayload(String kafkaPayload) {
        System.out.println("Payload received: " + kafkaPayload);
    }

    @Override
    public String eventType() {
        return "string.event.type";
    }
}
