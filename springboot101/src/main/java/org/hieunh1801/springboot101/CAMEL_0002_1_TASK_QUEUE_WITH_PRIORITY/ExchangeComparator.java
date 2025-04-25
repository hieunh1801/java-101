package org.hieunh1801.springboot101.CAMEL_0002_1_TASK_QUEUE_WITH_PRIORITY;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;

import java.util.Comparator;

@Slf4j
public class ExchangeComparator implements Comparator<Exchange> {
    @Override
    public int compare(Exchange e1, Exchange e2) {
        // Example: Assume priority is set in a header called "priority"
        Integer p1 = e1.getIn().getHeader("priority", 0, Integer.class);
        Integer p2 = e2.getIn().getHeader("priority", 0, Integer.class);
        int taskP1Id = e1.getIn().getHeader("taskId", Integer.class);
        int taskP2Id = e2.getIn().getHeader("taskId", Integer.class);
        String route1Id = e1.getIn().getHeader("routeId", String.class);
        String route2Id = e2.getIn().getHeader("routeId", String.class);
        log.info("Route1Id: {}, Route2Id: {}", route1Id, route2Id);
        log.info("Task1Id: {}, Task2Id: {}", taskP1Id, taskP2Id);
        log.info("P1: {}, P2: {}", p1, p2);
        return p2.compareTo(p1);
    }
}