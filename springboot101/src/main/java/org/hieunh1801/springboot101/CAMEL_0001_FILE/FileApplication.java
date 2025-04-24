/**
 * Working with Camel File
 */
package org.hieunh1801.springboot101.CAMEL_0001_FILE;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class FileApplication {

    @Autowired
    private ProducerTemplate producerTemplate;

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

    @GetMapping("/trigger")
    public String trigger() {
        producerTemplate.sendBody("direct:entry", "TEST");
        return "SUCCESS";
    }
}
