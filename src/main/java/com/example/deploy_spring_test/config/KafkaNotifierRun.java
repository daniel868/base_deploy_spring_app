package com.example.deploy_spring_test.config;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class KafkaNotifierRun implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private final KafkaConfig kafkaConfig;

    public KafkaNotifierRun(KafkaConsumer<String, String> kafkaConsumer, KafkaConfig kafkaConfig) {
        this.consumer = kafkaConsumer;
        this.kafkaConfig = kafkaConfig;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (kafkaConfig.getKafkaQueue()) {
                while (kafkaConfig.getHasNewMessage().get()) {
                    try {
                        kafkaConfig.getKafkaQueue().wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            synchronized (kafkaConfig.getKafkaQueue()) {
                if (!records.isEmpty()) {
                    kafkaConfig.getKafkaQueue().put("records", records);
                    kafkaConfig.getHasNewMessage().set(true);
                    kafkaConfig.getKafkaQueue().notifyAll();
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

