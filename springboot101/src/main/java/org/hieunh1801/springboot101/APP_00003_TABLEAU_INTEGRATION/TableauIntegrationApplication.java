package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TableauIntegrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(TableauIntegrationApplication.class);
        log.info("Hello WOrld");
    }
}
