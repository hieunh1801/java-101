package org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY.processors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private int taskId;
    private String from;
    private int fromTime;
    private String to;
    private int toTime;
}
