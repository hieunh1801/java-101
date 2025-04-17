package org.example.camel101.example.triggerapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class TriggerApi {
    @Autowired
    private ProducerTemplate producerTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TriggerApi.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/test1")
    public String test1() {
        producerTemplate.sendBody("direct:test1", "Trigger file processing");
        return "Test 1 is executed successfully";
    }

    @GetMapping("/test2")
    public String test2() {
        producerTemplate.sendBody("direct:test2", "Trigger file processing");
        return "Test 1 is executed successfully";
    }


    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:test2")
                        .log("Trigger Test2");

                from("direct:test1")
                        .log("Trigger Test1");
            }
        };
    }
}
