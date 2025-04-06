package org.example.camel101.misolution.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class DataToCsvStreamProcessor implements Processor {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> body = (Map<String, Object>) exchange.getIn().getBody();
        List<Object> data = (List<Object>) body.get("data");
        if (data == null) {
            return;
        }

        Set<String> rowKeys = new LinkedHashSet<>();
        for (Object row : data) {
            Map<String, Object> rowMap = objectMapper.convertValue(row, new TypeReference<>() {
            });
            rowKeys.addAll(rowMap.keySet());
        }

        CsvDataFormat csvFormat = new CsvDataFormat();
        log.info("Header {}", String.join(",", rowKeys));
        csvFormat.setHeader(String.join(",", rowKeys));
        csvFormat.setSkipHeaderRecord(false);
        csvFormat.setDelimiter(',');
        csvFormat.setQuoteMode(QuoteMode.ALL);
        csvFormat.setQuote('"');
        csvFormat.setEscape('\\');
        csvFormat.setAllowMissingColumnNames(true);
        csvFormat.setIgnoreEmptyLines(true);
        csvFormat.setSkipHeaderRecord(false);
    }
}
