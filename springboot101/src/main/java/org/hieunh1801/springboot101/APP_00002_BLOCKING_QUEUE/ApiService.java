package org.hieunh1801.springboot101.APP_00002_BLOCKING_QUEUE;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiService {

    public void executeApi(BlockingQueueService.Request request) throws InterruptedException {
//        log.info("[jobId-{}] API_SERVICE > START", request.getId());
        Thread.sleep(request.getRunTime());
//        log.info("[jobId-{}] API_SERVICE > STOP", request.getId());
    }
}
