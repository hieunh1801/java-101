package org.example.camel101.example.misolution.configuration;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class DestroyConfiguration {
    @Autowired
    private ExecutorService executorService;

    @PreDestroy
    public void destroy() {
        log.info("Shutting down executor service");
        executorService.shutdown();
    }
}
