/**
 * Working with Camel File
 */
package org.hieunh1801.springboot101.CAMEL_0002_0_TASK_QUEUE;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.hieunh1801.springboot101.CAMEL_0002_0_TASK_QUEUE.processors.Cafe24Extractor;
import org.hieunh1801.springboot101.CAMEL_0002_0_TASK_QUEUE.processors.Cafe24Loader;
import org.hieunh1801.springboot101.CAMEL_0002_0_TASK_QUEUE.processors.KakaoExtractor;
import org.hieunh1801.springboot101.CAMEL_0002_0_TASK_QUEUE.processors.KakaoLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
@RestController
public class TaskQueue {
//    static int taskCount = 0;
    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private Cafe24Extractor cafe24Extractor;
    @Autowired
    private Cafe24Loader cafe24Loader;
    @Autowired
    private KakaoExtractor kakaoExtractor;
    @Autowired
    private KakaoLoader kakaoLoader;

    @Autowired
    private CamelContext camelContext;

    public static void main(String[] args) {
        SpringApplication.run(TaskQueue.class, args);
    }

    @GetMapping("/trigger")
    public String trigger(@RequestParam int totalCount) {
        for (int taskCount = 0; taskCount < totalCount; taskCount++) {
            String from = taskCount % 2 == 0 ? "CAFE24" : "KAKAO";
            String to = taskCount % 2 == 1 ? "CAFE24" : "KAKAO";
            Map<String, Object> headers = new HashMap<>();
            headers.put("taskId", taskCount);
            headers.put("from", from);
            headers.put("to", to);
            headers.put("startTime", System.currentTimeMillis());
            String body = "Task " + taskCount;
            producerTemplate.sendBodyAndHeaders("direct:entry", body, headers);
        }
        return "SUCCESS";
    }

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:entry")
                        .routeId("entryRoute")
                        .log("taskId=[${header.taskId}] START: ${header.from} -> ${header.to}")
                        .process(exchange -> {
                            String from = exchange.getIn().getHeader("from", String.class);
                            String to = exchange.getIn().getHeader("to", String.class);
                            String fromQueue = from + "_QUEUE";
                            String toQueue = to + "_QUEUE";
                            exchange.getIn().setHeader("fromQueue", fromQueue);
                            exchange.getIn().setHeader("toQueue", toQueue);
                        })
                        .toD("seda:${header.fromQueue}");

                from("seda:CAFE24_QUEUE?concurrentConsumers=1")
                        // loader
                        .choice()
                        .when(header("to").isEqualTo("CAFE24"))
                        .process(cafe24Loader)
                        .to("direct:exit")
                        .end()
                        // extractor
                        .choice()
                        .when(header("from").isEqualTo("CAFE24"))
                        .process(cafe24Extractor)
                        .toD("seda:${header.toQueue}")
                        .end();

                from("seda:KAKAO_QUEUE?concurrentConsumers=1")
                        // loader
                        .choice()
                        .when(header("to").isEqualTo("KAKAO"))
                        .process(kakaoLoader)
                        .to("direct:exit")
                        .end()
                        // extractor
                        .choice()
                        .when(header("from").isEqualTo("KAKAO"))
                        .process(kakaoExtractor)
                        .toD("seda:${header.toQueue}")
                        .end();

                from("direct:exit")
                        .routeId("exitRoute")
                        .process(exchange -> {
                            Map<String, Object> headers = exchange.getIn().getHeaders();
                            long startTime = headers.get("startTime") != null
                                    ? Long.parseLong(headers.get("startTime").toString())
                                    : System.currentTimeMillis();
                            long endTime = System.currentTimeMillis();
                            exchange.getIn().setHeader("endTime", endTime);
                            exchange.getIn().setHeader("runTimeMs", endTime - startTime);
                        })
                        .log("taskId=[${header.taskId}] END ${header.taskId} in ${header.runTimeMs} ms");
            }
        };
    }

    @PreDestroy
    public void shutdown() {
        camelContext.stop();
    }

}
