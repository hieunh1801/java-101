package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION;

import lombok.extern.slf4j.Slf4j;
import org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau.TableauTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class TableauIntegrationApplication implements ApplicationRunner {

    @Autowired
    private TableauTest tableauTest;


    public static void main(String[] args) {
        SpringApplication.run(TableauIntegrationApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.signInTest();
        tableauTest.getUsersTest();
    }
}
