package org.hieunh1801.springboot101.CAMEL_0001_FILE.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FolderToFolderRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/camelfrom?noop=true")
                .routeId("FOLDER_TO_FOLDER")
                .log("EXTRACTED: ${file:name}")
                .to("file:src/main/resources/camelto")
                .log("LOADED: ${file:name}");
    }
}
