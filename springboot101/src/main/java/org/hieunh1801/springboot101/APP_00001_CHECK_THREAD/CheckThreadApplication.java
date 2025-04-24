package org.hieunh1801.springboot101.APP_00001_CHECK_THREAD;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class CheckThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckThreadApplication.class);
    }

    @GetMapping("/trigger")
    public String trigger(
            @RequestParam("sleep") int sleep
    ) throws InterruptedException {
        log.info("THREAD START");
        Thread.sleep(sleep);
        log.info("THREAD END");
        return "SUCCESS";
    }
}
