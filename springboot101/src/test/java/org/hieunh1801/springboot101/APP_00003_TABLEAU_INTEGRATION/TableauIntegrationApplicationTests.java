package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION;

import org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau.TableauApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class TableauIntegrationApplicationTests {
    Logger logger = Logger.getLogger(TableauIntegrationApplicationTests.class.getName());
    @Autowired
    private TableauApiService tableauApiServices;

    @Test
    void contextLoads() {
        logger.info("Hello world");
    }
}
