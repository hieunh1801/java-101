package org.example.camel101.example.misolution.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MisolutionLogProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> relayMap = (Map<String, Object>) exchange.getIn().getHeader("relayMap");
        if (relayMap == null) {
            return;
        }

        List<Cafe24PullProcessor.Cafe24Row> data = (List<Cafe24PullProcessor.Cafe24Row>) relayMap.get("data");

        for (Cafe24PullProcessor.Cafe24Row row : data) {
//            log.info("Insert into database: {}", row.id);
        }

        log.info("Saved total {}", data.size());
    }
}
