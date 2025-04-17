/**
 * PATTERN: ROUTING
 */
package org.example.camel101.pattern.routing_content_base;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Map<String, Object> body = new HashMap<>();
        producerTemplate.sendBody("direct:trigger", "test1");
        producerTemplate.sendBody("direct:trigger", "test2");
        producerTemplate.sendBody("direct:trigger", "test3");
        return "SUCCESS";
    }

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:trigger")
                        .choice()
                        .when(simple("${body} == 'test1'"))
                        .log("TO_001 -> ${body}")
                        .when(simple("${body} == 'test2'"))
                        .log("TO_002 -> ${body}")
                        .when(simple("${body} == 'test3'"))
                        .log("TO_003 -> ${body}")
                        .end()
                        .to("mock:result");

                from("direct:start")
                        .log("LOG ${body}");
            }
        };
    }
}
