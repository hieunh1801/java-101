package org.hieunh1801.springboot101.CAMEL_0002_TASK_QUEUE.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class KakaoLoader implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
        int taskId = (int) headers.get("taskId");
        long runTime = ThreadLocalRandom.current().nextInt(1, 10);
        log.info("taskId=[{}] KakaoLoader started wait {}s", taskId, runTime);
        Thread.sleep(runTime* 1000);
        log.info("taskId=[{}] KakaoLoader ended", taskId);
    }
}
