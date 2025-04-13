package org.example.camel101.misolution.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MisolutionStartJobProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> relayMap = (Map<String, Object>) exchange.getIn().getHeader("relayMap");

        String source = relayMap.get("source").toString();
        String target = relayMap.get("target").toString();
        log.info("START_TRANSFER_PROCESSOR FROM: {} TO: {}", source, target);
    }
}
