package org.example.camel101.apitojsontocsvtosftp;

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
public class ApiToJsonToCsvToSftp {
    public static void main(String[] args) {
        SpringApplication.run(ApiToJsonToCsvToSftp.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                String host = "mcjfm2d4ywsykrff1y2x8rgtvtm0.ftp.marketingcloudops.com";
                String port = "22";
                String username = "546003683_6";
                String password = "milvusftp1@3$";
                String directoryName = "Import/Test";

                String toSftp = String.format("sftp://%s@%s:%s/%s?password=%s&preferredAuthentications=publickey,password&serverHostKeys=ssh-rsa&fileName=output.csv",
                        username,
                        host,
                        port,
                        directoryName,
                        password
                );

                CsvDataFormat csvFormat = new CsvDataFormat();
                csvFormat.setHeader(String.join(",", Arrays.asList("userId", "id", "title", "body")));
                csvFormat.setSkipHeaderRecord(false);
                csvFormat.setDelimiter(',');
                csvFormat.setQuoteMode(QuoteMode.ALL);
                csvFormat.setQuote('"');
                csvFormat.setEscape('\\');
                csvFormat.setAllowMissingColumnNames(true);
                csvFormat.setIgnoreEmptyLines(true);
                csvFormat.setSkipHeaderRecord(false);


                from("timer://foo?fixedRate=true&period=5000&repeatCount=1")
                        .to("https://jsonplaceholder.typicode.com/posts")
                        .unmarshal()
                        .json(List.class)
                        .marshal(csvFormat)
                        .to(toSftp)
                        .log("Uploaded file");
            }
        };
    }
}
