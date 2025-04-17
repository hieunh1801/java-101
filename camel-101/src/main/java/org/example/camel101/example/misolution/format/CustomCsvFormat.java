package org.example.camel101.example.misolution.format;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("customCsvFormat")
public class CustomCsvFormat implements DataFormat {
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> relayMap = (Map<String, Object>) exchange
                .getIn()
                .getHeader("relayMap");

        List<Object> data = (List<Object>) relayMap.get("data");

        if (data == null || data.isEmpty()) {
            return;
        }

        Set<String> headers = new LinkedHashSet<>();
        List<Map<String, Object>> rowMaps = new ArrayList<>();

        for (Object row : data) {
            Map<String, Object> rowMap = objectMapper.convertValue(row, new TypeReference<>() {
            });
            headers.addAll(rowMap.keySet());
            rowMaps.add(rowMap);
        }

        try (Writer writer = new OutputStreamWriter(stream);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader(headers.toArray(new String[0]))
                     .withQuoteMode(QuoteMode.ALL))) {

            for (Map<String, Object> row : rowMaps) {
                List<String> values = headers.stream()
                        .map(h -> row.getOrDefault(h, "").toString())
                        .collect(Collectors.toList());
                printer.printRecord(values);
            }
        }

        exchange.getIn().setHeader("contentType", "text/csv");
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) {
        throw new UnsupportedOperationException("Unmarshal not supported.");
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
