package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION;

import lombok.extern.slf4j.Slf4j;
import org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau.CommonUtil;
import org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau.TableauApiService;
import org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau.TableauInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class TableauIntegrationApplication implements ApplicationRunner {

    @Autowired
    private TableauApiService tableauApiService;

    public static void main(String[] args) {
        SpringApplication.run(TableauIntegrationApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.signInTest();
        this.getUsersTest();
    }

    private void signInTest() throws InterruptedException {
        Map<String, Object> res = tableauApiService.signIn(
                TableauInformation.HOST,
                TableauInformation.SITE,
                TableauInformation.API_VERSION,
                TableauInformation.PAT_NAME,
                TableauInformation.PAT_VALUE
        );
        log.info("res={}", res);
    }

    private void getUsersTest() throws InterruptedException {
        Map<String, Object> tableauInfo = TableauInformation.getInfo();
        Map<String, Object> usersRes = tableauApiService.getUsers(tableauInfo);
        List<Map<String, Object>> users = CommonUtil.getNestedValue(usersRes, "users.user", new ArrayList<>());
        List<Map<String, Object>> userDetails = new ArrayList<>();
        int count = 0;
        while (true) {
            for (Map<String, Object> user : users) {
                String userId = CommonUtil.getNestedValue(user, "id", null);
                if (user == null) {
                    continue;
                }
                Map<String, Object> userDetailRes = tableauApiService.getUserDetail(tableauInfo, userId);
                log.info("[{}] {} -> {}", count++, userId, userDetailRes);
            }
        }

    }
}
