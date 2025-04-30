package org.hieunh1801.springboot101.CAMEL_0004_PAUSE_RESUME;

import org.apache.camel.builder.RouteBuilder;

public class TaskRouteBuilder extends RouteBuilder {
    private final String taskId;

    public TaskRouteBuilder(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void configure() {
        int step = 0;
        from("seda:" + taskId)  // async queue
                .routeId(taskId)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000)
                .log("Task " + taskId + " step: " + step++)
                .delay(1000);
    }
}

