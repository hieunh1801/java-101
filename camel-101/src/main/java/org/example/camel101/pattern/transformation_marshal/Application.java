package org.example.camel101.pattern.transformation_marshal;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@SpringBootApplication
public class Application {
    @Autowired
    private ProducerTemplate producerTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @GetMapping("/trigger")
    public String trigger() {
        new Thread(() -> {
            Map<String, Object> data = new HashMap<>();
            producerTemplate.sendBody("direct:trigger", """
                    {"a": 1}
                    """);
        }).start();
        return "SUCCESS";
    }

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:trigger")
                        .unmarshal().json()          // JSON → Java Object
                        .process(exchange -> {
                            Map<String, Object> body = exchange.getIn().getBody(Map.class);
                            body.put("status", "processed");
                            log.info("body: {}", body);
                            exchange.getIn().setBody(Collections.singleton(body));
                        })
//                        .marshal().json(true)            // Java Object → JSON
                        .marshal().csv()
                        .to("file:src/resources/output?noop=true&fileName=output.json");
            }
        };
    }
}
