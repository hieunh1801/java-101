package org.hieunh1801.springboot101.CAMEL_0003_QUARTZ.quartz;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduledJob {
    private String routeId; // routeId dùng để stop/resume
    private String cronExpression;
    private String description;
    private String status; // STARTED, STOPPED, v.v.
    private LocalDateTime createdAt;
}
