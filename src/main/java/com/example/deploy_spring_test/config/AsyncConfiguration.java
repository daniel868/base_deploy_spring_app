package com.example.deploy_spring_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfiguration {

    @Bean("executorService")
    public ExecutorService provideExecutor() {
        return Executors.newCachedThreadPool();
    }

}
