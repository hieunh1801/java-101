package org.example.camel101.misolution;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.example.camel101.misolution.processor.Cafe24ExtractProcessor;
import org.example.camel101.misolution.processor.Cafe24LogProcessor;
import org.example.camel101.misolution.processor.DataToCsvStreamProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@SpringBootApplication
@RestController
public class MisolutionApplication {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private Cafe24ExtractProcessor cafe24ExtractProcessor;
    @Autowired
    private Cafe24LogProcessor cafe24LogProcessor;

    @Autowired
    private DataToCsvStreamProcessor dataToCsvProcessor;

    public static void main(String[] args) {
        SpringApplication.run(MisolutionApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/trigger")
    public Map<String, Object> trigger(
            @RequestParam("source") String source,
            @RequestParam("target") String target
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("source", source);
        body.put("target", target);

        Map<String, Object> headers = new HashMap<>();
        headers.put("routeKey", String.format("%s_%s", source.toUpperCase(), target.toUpperCase()));
        producerTemplate.sendBodyAndHeaders("direct:entry", body, headers);
        return body;
    }


    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:entry")
                        .routeId("MainTriggerRouter")
                        .log("==> Trigger: ${header.routeKey}")
                        .toD("direct:${header.routeKey}");

                from("direct:CAFE24_SFTP")
                        .log("START ${header.routeKey} ${body}")
                        .process(cafe24ExtractProcessor)
                        .process(cafe24LogProcessor)
                        .process(dataToCsvProcessor);

                from("direct:CAFE24_S3")
                        .log("START ${header.routeKey} ${body}");

                from("direct:KAKAO_SFTP")
                        .log("START ${header.routeKey} ${body}");

                from("direct:KAKAO_S3")
                        .log("START ${header.routeKey} ${body}");

            }
        };
    }


    boolean equal(final String a, final String b) {
        if (a == null && b == null) {
            return true;
        }
        final String aNormalized = a == null ? null : a.trim().toLowerCase();
        final String bNormalized = b == null ? null : b.trim().toLowerCase();
        return Objects.equals(aNormalized, bNormalized);
    }
}
