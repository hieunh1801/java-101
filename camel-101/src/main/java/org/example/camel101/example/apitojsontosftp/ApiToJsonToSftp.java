package org.example.camel101.example.apitojsontosftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class ApiToJsonToSftp {
    public static void main(String[] args) {
        SpringApplication.run(ApiToJsonToSftp.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                String host = "mcjfm2d4ywsykrff1y2x8rgtvtm0.ftp.marketingcloudops.com";
                String port = "22";
                String username = "546003683_6";
                String password = "milvusftp1@3$";
                String directoryName = "Import/Test";

                String toSftp = String.format("sftp://%s@%s:%s/%s?password=%s&preferredAuthentications=publickey,password&serverHostKeys=ssh-rsa&fileName=output.json",
                        username,
                        host,
                        port,
                        directoryName,
                        password
                );

                from("timer://foo?fixedRate=true&period=5000&repeatCount=1")
                        .to("https://jsonplaceholder.typicode.com/posts")
                        .log("Response ${body}")
                        .to(toSftp)
                        .log("Uploaded file");
            }
        };
    }
}
