package org.hieunh1801.springboot101.CAMEL_0003_QUARTZ.quartz;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuartzService {
    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    public void addJob(String routeId, String cronExpression) throws Exception {
        String quartzUri = String.format("quartz://group/%s?cron=%s", routeId, cronExpression);

        RouteBuilder dynamicRoute = new RouteBuilder() {
            @Override
            public void configure() {
                from(quartzUri)
                        .routeId(routeId)
                        .log("🔥 [Dynamic Quartz] Job " + routeId + " triggered at ${date:now}");
            }
        };

        ScheduledJob job = new ScheduledJob();
        job.setRouteId(routeId);
        job.setCronExpression(cronExpression);
        job.setDescription("Dynamic job " + routeId);
        job.setStatus("STARTED");
        job.setCreatedAt(LocalDateTime.now());

        // save to database
        camelContext.addRoutes(dynamicRoute);
    }

    public void pauseJob(String routeId) throws Exception {
        camelContext.getRouteController().stopRoute(routeId);
        // pause & save to database
    }

    public void resumeJob(String routeId) throws Exception {
        camelContext.getRouteController().startRoute(routeId);
        // resume & save to database
    }

    public String getStatus(String routeId) throws Exception {
        return camelContext.getRouteController().getRouteStatus(routeId).name();
    }

    @PostConstruct
    public void init() throws Exception {
        List<ScheduledJob> jobs = new ArrayList<>();

        for (ScheduledJob job : jobs) {
            // Nếu route đã tồn tại, bỏ qua
            if (camelContext.getRouteController().getRouteStatus(job.getRouteId()) != null) continue;

            String uri = String.format("quartz://group/%s?cron=%s", job.getRouteId(), job.getCronExpression());

            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from(uri)
                            .routeId(job.getRouteId())
                            .log("🔁 Job " + job.getRouteId() + " executed.");
                }
            });

            // Nếu job đang ở trạng thái STOPPED thì tạm dừng route sau khi tạo
            if ("STOPPED".equalsIgnoreCase(job.getStatus())) {
                camelContext.getRouteController().stopRoute(job.getRouteId());
            }
        }

        log.info("✅ All scheduled jobs initialized from DB.");
    }
}
