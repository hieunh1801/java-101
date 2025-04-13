package org.example.camel101.misolution.routes;

import org.apache.camel.builder.RouteBuilder;
import org.example.camel101.misolution.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cafe24Route extends RouteBuilder {
    @Autowired
    private Cafe24PullProcessor cafe24PullProcessor;

    @Autowired
    private MisolutionLogProcessor misolutionLogProcessor;

    @Autowired
    private MisolutionStartJobProcessor misolutionStartJobProcessor;

    @Autowired
    private MisolutionStopJobProcessor misolutionStopJobProcessor;

    @Autowired
    private SftpPushUriBuilderProcessor sftpPushUriBuilderProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:CAFE24___SFTP")
                .log("START ${header.routeKey} ${body}")
                .process(misolutionStartJobProcessor)
                .process(cafe24PullProcessor)
                .process(misolutionLogProcessor)
                .marshal("customCsvFormat")
                .process(sftpPushUriBuilderProcessor)
                .toD("${header.sftpUri}")
                .process(misolutionStopJobProcessor);

        from("direct:CAFE24___S3")
                .log("START ${header.routeKey} ${body}");
    }
}
