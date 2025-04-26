package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@DependsOn("tableauInformation")
public class TableauTest {
    @Autowired
    private TableauInformation tableauInformation;
    @Autowired
    private TableauApiService tableauApiService;

    public void signInTest() throws InterruptedException {
        Map<String, Object> res = tableauApiService.signIn(
                tableauInformation.HOST,
                tableauInformation.SITE,
                tableauInformation.API_VERSION,
                tableauInformation.PAT_NAME,
                tableauInformation.PAT_VALUE
        );
        log.info("res={}", res);
    }

    public void getUsersTest() throws InterruptedException {
        Map<String, Object> tableauInfo = tableauInformation.getInfo();
        Map<String, Object> usersRes = tableauApiService.getUsers(tableauInfo);
        List<Map<String, Object>> users = CommonUtil.getNestedValue(usersRes, "users.user", new ArrayList<>());
        List<Map<String, Object>> userDetails = new ArrayList<>();
        int count = 0;
        while (true) {
            for (Map<String, Object> user : users) {
                Thread.sleep(30*1000);
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
