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
public class Cafe24ExtractProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> body = (Map<String, Object>) exchange.getIn().getBody();
        EasyRandom easyRandom = new EasyRandom();

        List<Cafe24Row> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(easyRandom.nextObject(Cafe24Row.class));
        }

        // update body
        body.put("data", data);
        exchange.getIn().setBody(body);
    }

    @Data
    static class Cafe24Row {
        String id;
        String username;
        Integer age;
        String orderId;
    }
}

