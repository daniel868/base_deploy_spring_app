package com.example.deploy_spring_test;

import com.example.deploy_spring_test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeploySpringTestApplication {
    private static final Logger logger = LoggerFactory.getLogger(DeploySpringTestApplication.class);

    @Value("${selected.environment}")
    private String selectedEnvironment;
    @Value("${kafka.host}")
    private String kafkaHost;

    public static void main(String[] args) {
        SpringApplication.run(DeploySpringTestApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepository) {
        return args -> {
            logger.debug("Selected environment: {}", selectedEnvironment);
            logger.debug("Kafka host: {}", kafkaHost);
        };
    }

}
