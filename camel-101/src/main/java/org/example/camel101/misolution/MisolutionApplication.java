package org.example.camel101.misolution;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Slf4j
@SpringBootApplication
@RestController
public class MisolutionApplication {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ExecutorService executorService;


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
        Map<String, Object> relayMap = new HashMap<>();
        relayMap.put("source", source);
        relayMap.put("target", target);
        Map<String, Object> headers = new HashMap<>();
        headers.put("routeKey", String.format("%s_%s", source.toUpperCase(), target.toUpperCase()));
        headers.put("relayMap", relayMap);
        headers.put("source", source);
        headers.put("target", target);
        executorService.submit(() -> {
            producerTemplate.sendBodyAndHeaders("direct:entry", body, headers);
        });
        return headers;
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
