package org.example.camel101.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

@Slf4j
public class TemplateApplication {
    public static void main(String[] args) throws Exception {
        // Create CamelContext
        try (CamelContext context = new DefaultCamelContext()) {

            // Add a route that sends files to SFTP
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer:timer?period=5000&repeatCount=1")
                            .log("Triggered");
                }
            });

            context.start();
            Thread.sleep(15000);
            context.stop();
        }
    }
}
