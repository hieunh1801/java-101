package org.hieunh1801.springboot101.APP_00002_BLOCKING_QUEUE;


import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Slf4j
@Service
public class BlockingQueueService {
    @Autowired
    private ApiService apiService;
    private final int POOL_SIZE = 1;
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(POOL_SIZE);

    public BlockingQueueService() {
        log.info("INIT BLOCKING QUEUE SERVICE");
        for (int i = 0; i < POOL_SIZE; i++) {
            int workerId = i + 1;
            executor.scheduleAtFixedRate(() -> processQueue(workerId), 0, 2, TimeUnit.SECONDS);
        }
//        executor.scheduleAtFixedRate(this::processQueue, 0, 1, TimeUnit.SECONDS); // 1 request/giây
    }

    public void enqueue(Request request) {
        log.info("QUEUE > ADD: {}", request);
        queue.offer(request);
    }

    private void processQueue(int workerId) {
        Request request = queue.poll();
        log.info("[worker-{}] QUEUE > CHECK", workerId);
        if (request == null) {
            return;
        }

        log.info("[worker-{}__jobId-{}] QUEUE > PROCESS START", workerId, request.getId());
        try {
            this.apiService.executeApi(request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("[worker-{}__jobId-{}] QUEUE > PROCESS END", workerId, request.getId());
        }

    }

    @Data
    @ToString
    public static class Request {
        int id;
        int runTime;
    }
}
