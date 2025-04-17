package org.example.camel101.example.misolution.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class ExecutorConfig {


    @Bean
    public ExecutorService executorService() {
        // Tạo thread pool với tối đa 10 thread
        return Executors.newFixedThreadPool(2);
    }
}
