package org.example.camel101.example.filetofile;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class FileToFileApplication {
    @Autowired
    private ProducerTemplate producerTemplate;


    public static void main(String[] args) {
        SpringApplication.run(FileToFileApplication.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("file:src/main/resources/filetofile/input?noop=true")
                        .log("Processing file: ${file:name}")
                        .to("file:src/main/resources/filetofile/output?noop=true")
                        .log("Successfully processed file: ${file:name}");
            }
        };
    }
}
