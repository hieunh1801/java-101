package org.hieunh1801.springboot101.CAMEL_0001_FILE.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FileToLogRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/camelfrom?fileName=test.txt&noop=true")
                .log("Reading file: ${header.CamelFileName}")
                .log("File content: ${body}")
                .process(exchange -> {
                    // Xử lý nội dung file ở đây
                    String content = exchange.getIn().getBody(String.class);
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    // ... thêm logic xử lý của bạn
                    log.info(content);
                    log.info(headers.toString());
                });
    }
}
