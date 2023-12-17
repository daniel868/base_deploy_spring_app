package com.example.deploy_spring_test.handlers;

public class Test2EventKafkaHandler extends BaseKafkaHandler<Integer> {
    @Override
    public void handlePayload(Integer kafkaPayload) {
        System.out.println("Test2EventKafkaHandler handle payload: " + kafkaPayload);
    }

    @Override
    public String eventType() {
        return "integer.event.type";
    }
}
