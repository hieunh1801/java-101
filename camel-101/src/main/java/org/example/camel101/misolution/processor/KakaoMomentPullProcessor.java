package org.example.camel101.misolution.processor;

import lombok.Data;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jeasy.random.EasyRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KakaoMomentPullProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> body = (Map<String, Object>) exchange.getIn().getBody();
        EasyRandom easyRandom = new EasyRandom();
        List<KakaoRow> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            data.add(easyRandom.nextObject(KakaoRow.class));
        }
        // update body
        body.put("data", data);
        exchange.getIn().setBody(body);
    }

    @Data
    static class KakaoRow {
        String id;
        String username;
        Integer age;
        String orderId;
    }
}
