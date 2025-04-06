package org.example.camel101.apitocsvfile;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@RestController
public class ApiToCsvFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiToCsvFileApplication.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                CsvDataFormat csvFormat = new CsvDataFormat();
                csvFormat.setHeader(String.join(",", Arrays.asList("userId", "id", "title", "body")));
                csvFormat.setSkipHeaderRecord(false);
                csvFormat.setDelimiter(',');
                csvFormat.setQuoteMode(QuoteMode.ALL);
                csvFormat.setQuote('"');
                csvFormat.setEscape('\\');
                csvFormat.setAllowMissingColumnNames(true);
                csvFormat.setIgnoreEmptyLines(true);


                from("timer://foo?fixedRate=true&period=5000&repeatCount=1")
                        .log("Trigger ApiToCsvFile")
                        .to("https://jsonplaceholder.typicode.com/posts")
                        .unmarshal()
                        .json(List.class)
                        .marshal(csvFormat)
                        .to("file:src/main/resources/apitocsvfile?fileName=data.csv")
                        .log("Write to file success ${file:name}");
            }
        };
    }
}
