package org.hieunh1801.springboot101.CAMEL_0003_QUARTZ.quartz;

import java.time.LocalDateTime;

public class ScheduledJobService {
    private String routeId; // routeId dùng để stop/resume
    private String cronExpression;
    private String description;
    private String status; // STARTED, STOPPED, v.v.
    private LocalDateTime createdAt;
}
