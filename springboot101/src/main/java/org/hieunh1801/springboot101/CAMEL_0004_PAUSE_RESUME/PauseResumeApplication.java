package org.hieunh1801.springboot101.CAMEL_0004_PAUSE_RESUME;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PauseResumeApplication implements ApplicationRunner {

    @Autowired
    private TaskManager manager;

    public static void main(String[] args) {
        SpringApplication.run(PauseResumeApplication.class);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.start();

        manager.addTask("taskA");
        manager.addTask("taskB");
        manager.sendToTask("taskA", "Hello A");
        manager.sendToTask("taskB", "Hello B");

        Thread.sleep(1000);
        manager.pauseTask("taskA");
    }
}
