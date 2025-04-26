package org.hieunh1801.queue101;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ApiService {

    public void executeApi(BlockingQueueService.Request request) throws InterruptedException {
//        log.info("[jobId-{}] API_SERVICE > START", request.getId());
        Thread.sleep(request.getRunTime());
//        log.info("[jobId-{}] API_SERVICE > STOP", request.getId());
    }
}
