package org.example.camel101.misolution.processor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class Cafe24PullProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> relayMap = (Map<String, Object>) exchange.getIn().getHeader("relayMap");
        EasyRandom easyRandom = new EasyRandom();

        List<Cafe24Row> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            data.add(easyRandom.nextObject(Cafe24Row.class));
        }
        log.info("COUNT={}", data.size());
        relayMap.put("data", data);
        exchange.getIn().setHeader("relayMap", relayMap);
    }

    @Data
    static class Cafe24Row {
        String id;
        String username;
        Integer age;
        String orderId;
    }
}

