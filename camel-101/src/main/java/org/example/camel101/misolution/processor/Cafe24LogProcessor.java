package org.example.camel101.misolution.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Cafe24LogProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> body = (Map<String, Object>) exchange.getIn().getBody();
        List<Cafe24ExtractProcessor.Cafe24Row> data = (List<Cafe24ExtractProcessor.Cafe24Row>) body.get("data");
        if (data == null) {
            return;
        }

        for (Cafe24ExtractProcessor.Cafe24Row row : data) {
            log.info("Insert into database: {}", row.id);
        }
    }
}
