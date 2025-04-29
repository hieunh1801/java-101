package org.hieunh1801.springboot101.CAMEL_0003_QUARTZ.quartz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QuartzRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("quartz://myGroup/myTimer?cron=0/10+*+*+*+*+?")
                .log("🕒 Triggered Quartz Job at ${date:now}");
    }
}
