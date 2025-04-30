package org.hieunh1801.springboot101.CAMEL_0004_PAUSE_RESUME;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskManager {
    @Autowired
    private CamelContext context;

    public void addTask(String taskId) throws Exception {
        context.addRoutes(new TaskRouteBuilder(taskId));
    }

    public void pauseTask(String taskId) throws Exception {
        context.getRouteController().suspendRoute(taskId);
        System.out.println("Task " + taskId + " đã bị pause");
    }

    public void resumeTask(String taskId) throws Exception {
        context.getRouteController().resumeRoute(taskId);
        System.out.println("Task " + taskId + " đã resume");
    }

    public void sendToTask(String taskId, Object body) {
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBody("seda:" + taskId, body);
    }
}
