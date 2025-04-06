package org.example.camel101.apitojsonfile;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class ApiToJsonFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiToJsonFileApplication.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("timer:foo?repeatCount=1")
                        .log("Trigger API")
                        .to("https://jsonplaceholder.typicode.com/posts")
                        .to("file:src/main/resources/apitojsonfile?fileName=data.json")  // Ghi vào file output
                        .log("File saved as ${file:name}");
            }
        };
    }
}
