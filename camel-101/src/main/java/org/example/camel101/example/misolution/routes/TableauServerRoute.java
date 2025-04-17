package org.example.camel101.example.misolution.routes;

import org.apache.camel.builder.RouteBuilder;
import org.example.camel101.example.misolution.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableauServerRoute extends RouteBuilder {
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
        from("direct:TABLEAU_SERVER___TABLEAU_CLOUD")
                .log("START ${header.routeKey} ${body}");

        from("direct:TABLEAU_SERVER___SFTP")
                .log("START ${header.routeKey} ${body}");

        from("direct:TABLEAU_SERVER___S3")
                .log("START ${header.routeKey} ${body}");
    }
}