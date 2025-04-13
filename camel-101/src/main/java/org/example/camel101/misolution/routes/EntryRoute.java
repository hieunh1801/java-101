package org.example.camel101.misolution.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EntryRoute extends RouteBuilder {

    private void processor(Exchange exchange) {

        String source = exchange.getIn().getHeader("source", String.class);
        String target = exchange.getIn().getHeader("target", String.class);

        String routeKey = source.toUpperCase() + "___" + target.toUpperCase();
        exchange.getIn().setHeader("routeKey", routeKey);
    }

    @Override
    public void configure() throws Exception {
        from("direct:entry")
                .routeId("MainTriggerRouter")
                .process(this::processor)
                .log("==> Trigger: ${header.routeKey}")
                .toD("direct:${header.routeKey}");
    }
}