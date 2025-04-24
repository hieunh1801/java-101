package org.hieunh1801.springboot101.APP_00002_BLOCKING_QUEUE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@SpringBootApplication
public class BlockingQueueApplication {

    @Autowired
    BlockingQueueService blockingQueueService;

    private int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(BlockingQueueApplication.class, args);
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
