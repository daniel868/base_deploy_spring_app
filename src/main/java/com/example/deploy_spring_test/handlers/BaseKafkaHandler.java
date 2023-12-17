package com.example.deploy_spring_test.handlers;

public abstract class BaseKafkaHandler<K> {
    public abstract void handlePayload(K kafkaPayload);
    public abstract String eventType();
}

