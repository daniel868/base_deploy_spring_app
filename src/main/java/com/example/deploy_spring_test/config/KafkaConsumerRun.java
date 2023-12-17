package com.example.deploy_spring_test.config;

import com.example.deploy_spring_test.handlers.BaseKafkaHandler;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.LinkedList;
import java.util.List;

public class KafkaConsumerRun implements Runnable {
    private KafkaConsumer<String, String> consumer;
    private KafkaConfig kafkaConfig;
    private List<BaseKafkaHandler> handlers;

    public KafkaConsumerRun(KafkaConsumer<String, String> consumer, KafkaConfig kafkaConfig) {
        this.consumer = consumer;
        this.kafkaConfig = kafkaConfig;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (kafkaConfig.getKafkaQueue()) {
                while (!kafkaConfig.getHasNewMessage().get()) {
                    try {
                        kafkaConfig.getKafkaQueue().wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            ConsumerRecords<String, String> records = kafkaConfig.getKafkaQueue().get("records");

            if (!records.isEmpty()) {
                records.forEach(record -> {
                    this.handlers.forEach(handler -> {
                        if ("string.event.type".equalsIgnoreCase(handler.eventType())) {
                            handler.handlePayload(record.value());
                        }
                    });
                });
            }

            synchronized (kafkaConfig.getKafkaQueue()) {
                kafkaConfig.getHasNewMessage().set(false);
                kafkaConfig.getKafkaQueue().notifyAll();
            }
        }
    }

    public void addKafkaHandler(BaseKafkaHandler handler) {
        if (this.handlers == null) {
            this.handlers = new LinkedList<>();
        }
        this.handlers.add(handler);
    }
}
