package org.example.camel101.kakaocrawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class KakaoCrawlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KakaoCrawlerApplication.class, args);
    }

    @Bean
    RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                String momentUrl = "https://apis.moment.kakao.com";
                String getAdAccountsUrl = momentUrl + "/openapi/v4/adAccounts?config=ON,OFF&page=0&size=2";
                String authToken = "Bearer b-OQREMy5x3RJEPStb8sUu01l0SIiuqFAAAAAwo8JFkAAAGVfd7IeE41iD77kjRL";
//                from("timer://fetchAdAccounts?fixedRate=true&period=60000&repeatCount=0")
//                        .setHeader("Authorization", constant(authToken)) // Thêm header Authorization
//                        .setHeader("Accept", constant("application/json"))
//                        .to(getAdAccountsUrl)
//                        .unmarshal()
//                        .json()
//                        .setBody()
//                        .jsonpath("$.content")
//                        .split(body())
//                        .setHeader("adAccountId", simple("${body[id]}")) // Lưu campaignId vào header
//                        .setHeader("Authorization", constant(authToken))
//                        .to("https://apis.moment.kakao.com/openapi/v4/campaigns")
////                        .unmarshal()
////                        .json()
////                        .marshal()
////                        .json()
////                        .to("file:src/main/resources/kakaocrawler?fileName=campaigns.csv")
//                        .log("Created File Success");

                from("timer://fetchAdAccounts?fixedRate=true&period=60000&repeatCount=0")
                        .setHeader("Authorization", constant(authToken)) // Thêm header Authorization
                        .setHeader("Accept", constant("application/json"))
                        .to(getAdAccountsUrl)
//                        .unmarshal()
//                        .json()
//                        .setBody()
//                        .jsonpath("$.content")
//                        .marshal()
//                        .json()
                        .to("file:src/main/resources/kakaocrawler?fileName=adAccounts.json")
                        .log("Created File Success");
            }
        };
    }
}
