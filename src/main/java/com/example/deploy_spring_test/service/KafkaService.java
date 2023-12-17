package com.example.deploy_spring_test.service;

import com.example.deploy_spring_test.config.KafkaConfig;
import com.example.deploy_spring_test.config.KafkaConsumerRun;
import com.example.deploy_spring_test.config.KafkaNotifierRun;
import com.example.deploy_spring_test.handlers.TestEventKafkaHandler;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class KafkaService {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private TestEventKafkaHandler testEventKafkaHandler;

    @PostConstruct
    public void initKafka() {
        KafkaNotifierRun kafkaNotifierRun = new KafkaNotifierRun(consumer, kafkaConfig);
        KafkaConsumerRun kafkaConsumerRun = new KafkaConsumerRun(consumer, kafkaConfig);
        kafkaConsumerRun.addKafkaHandler(testEventKafkaHandler);

//        executorService.submit(kafkaNotifierRun);
//        executorService.submit(kafkaConsumerRun);
    }
}
