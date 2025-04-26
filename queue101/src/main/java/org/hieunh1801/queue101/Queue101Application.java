package org.hieunh1801.queue101;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@SpringBootApplication
public class Queue101Application {

    @Autowired
    BlockingQueueService blockingQueueService;

    private int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(Queue101Application.class, args);
    }

    @GetMapping("/trigger")
    public String trigger() {
        for (int i = 0; i < 100; i++) {
            BlockingQueueService.Request request = new BlockingQueueService.Request();
            int randomNumber = ThreadLocalRandom.current().nextInt(1,2);
            request.setId(count);
            request.setRunTime(randomNumber*1000);
            count++;
            blockingQueueService.enqueue(request);
        }


        return "SUCCESS";
    }
}
