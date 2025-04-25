package org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY;

import org.apache.camel.component.seda.PriorityBlockingQueueFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {
    @Bean
    public PriorityBlockingQueueFactory priorityQueueFactory() {
        PriorityBlockingQueueFactory factory = new PriorityBlockingQueueFactory();
        factory.setComparator(new ExchangeComparator());
        return factory;
    }
}
