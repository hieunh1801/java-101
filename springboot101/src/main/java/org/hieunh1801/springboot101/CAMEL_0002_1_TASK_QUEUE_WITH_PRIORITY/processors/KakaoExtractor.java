package org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class KakaoExtractor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
        int taskId = (int) headers.get("taskId");
        int runTime = (int) headers.get("fromTime");
        int priority = (int) headers.get("priority");
        log.info("taskId=[{}]  priority=[{}] KakaoExtractor started wait {}s", taskId, priority, runTime);
        Thread.sleep(runTime*1000L);
        log.info("taskId=[{}] KakaoExtractor ended", taskId);
    }
}
