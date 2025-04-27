/**
 * Working with Camel File
 */
package org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY.processors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
@RestController
public class TaskQueuePriority implements ApplicationRunner {
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
        SpringApplication.run(TaskQueuePriority.class, args);
    }

    @GetMapping("/trigger")
    public String trigger() {


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
                            long startTime = System.currentTimeMillis();
                            exchange.getIn().setHeader("startTime", startTime);
                            exchange.getIn().setHeader("fromQueue", fromQueue);
                            exchange.getIn().setHeader("toQueue", toQueue);
                        })
                        .setHeader("priority").constant(0)
                        .toD("seda:${header.fromQueue}");

                from("seda:CAFE24_QUEUE?queueFactory=#priorityQueueFactory&concurrentConsumers=1")
                        // loader
                        .choice()
                        .when(header("to").isEqualTo("CAFE24"))
                        .process(cafe24Loader)
                        .to("direct:exit")
                        .end()
                        // extractor
                        .choice()
                        .when(header("from").isEqualTo("CAFE24"))
                        .setHeader("priority").constant(1)
                        .process(cafe24Extractor)
                        .toD("seda:${header.toQueue}")
                        .end();

                from("seda:KAKAO_QUEUE?queueFactory=#priorityQueueFactory&concurrentConsumers=1")
                        // loader
                        .choice()
                        .when(header("to").isEqualTo("KAKAO"))
                        .process(kakaoLoader)
                        .to("direct:exit")
                        .end()
                        // extractor
                        .choice()
                        .when(header("from").isEqualTo("KAKAO"))
                        .setHeader("priority").constant(1)
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

                            long handleStartTime = (long) headers.get("handleStartTime");
                            long handleEndTime = (long) headers.get("handleEndTime");
                            long handleTime = handleEndTime - handleStartTime;
                            exchange.getIn().setHeader("handleTime", handleTime);
                        })
                        .log("taskId=[${header.taskId}] END ${header.taskId} in ${header.runTimeMs} ms; handleTime=${header.handleTime}");
            }
        };
    }

    @PreDestroy
    public void shutdown() {
        camelContext.stop();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Không hoạt động
        List<Task> tasks = new ArrayList<>();
        int count = 1;
//        tasks.add(new Task(count++,"CAFE24", 2, "KAKAO", 5));
//        tasks.add(new Task(count++,"KAKAO", 5, "CAFE24", 2));
//        tasks.add(new Task(count++,"KAKAO", 5, "CAFE24", 2));

        tasks.add(new Task(count++,"CAFE24", 10, "KAKAO", 10));
        tasks.add(new Task(count++,"KAKAO", 5, "CAFE24", 7));
        tasks.add(new Task(count++,"KAKAO", 6, "CAFE24", 4));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));
//        tasks.add(new Task(count++,"KAKAO", 10, "CAFE24", 10));

        for (Task task: tasks) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("taskId", task.getTaskId());
            headers.put("from", task.getFrom());
            headers.put("fromTime", task.getFromTime());
            headers.put("to", task.getTo());
            headers.put("toTime", task.getToTime());
            producerTemplate.sendBodyAndHeaders("direct:entry", task, headers);
        }
    }
}
