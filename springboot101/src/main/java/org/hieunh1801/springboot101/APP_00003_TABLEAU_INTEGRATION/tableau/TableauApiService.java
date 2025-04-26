package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TableauApiService {

    @Autowired
    private TableauInformation tableauInformation;
    @Autowired
    private TableauApiClient tableauApiClient;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Map<String, Object> signIn(
            String host,
            String site,
            String apiVersion,
            String patName,
            String patValue) {
        log.info("SIGNING IN...");
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> requestCredentials = new HashMap<>();
        Map<String, String> requestSite = new HashMap<>();


        requestSite.put("contentUrl", site);
        requestCredentials.put("personalAccessTokenName", patName);
        requestCredentials.put("personalAccessTokenSecret", patValue);
        requestCredentials.put("site", requestSite);
        requestBody.put("credentials", requestCredentials);

        String contentTypeHeader = "application/json";
        String acceptHeader = "application/json";

        String uriPath = String.format("/api/%s/auth/signin", apiVersion);
        URI uri = URI.create(String.format("%s%s", host, uriPath));
        Map<String, Object> res = tableauApiClient.signIn(uri, contentTypeHeader, acceptHeader, requestBody);
        if (res == null) {
            return null;
        }
        return CommonUtil.getNestedValue(res, "credentials", null);
    }


    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public Map<String, Object> getUsers(Map<String, Object> tableauInfo) {
        String host = CommonUtil.getNestedValue(tableauInfo, "host", null);
        String site = CommonUtil.getNestedValue(tableauInfo, "site", null);
        String siteId = CommonUtil.getNestedValue(tableauInfo, "siteId", null);
        String patName = CommonUtil.getNestedValue(tableauInfo, "patName", null);
        String patValue = CommonUtil.getNestedValue(tableauInfo, "patValue", null);
        String apiVersion = CommonUtil.getNestedValue(tableauInfo, "apiVersion", null);


        String contentTypeHeader = "application/json";
        String acceptHeader = "application/json";

        String uriPath = String.format("/api/%s/sites/%s/users", apiVersion, siteId);
        URI uri = URI.create(String.format("%s%s", host, uriPath));
        return tableauApiClient.get(tableauInfo, uri);
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public Map<String, Object> getUserDetail(Map<String, Object> tableauInfo, String userId) {
        String host = CommonUtil.getNestedValue(tableauInfo, "host", null);
        String site = CommonUtil.getNestedValue(tableauInfo, "site", null);
        String siteId = CommonUtil.getNestedValue(tableauInfo, "siteId", null);
        String patName = CommonUtil.getNestedValue(tableauInfo, "patName", null);
        String patValue = CommonUtil.getNestedValue(tableauInfo, "patValue", null);
        String apiVersion = CommonUtil.getNestedValue(tableauInfo, "apiVersion", null);

        String uriPath = String.format("/api/%s/sites/%s/users/%s", apiVersion, siteId, userId);
        URI uri = URI.create(String.format("%s%s", host, uriPath));
        return tableauApiClient.get(tableauInfo, uri);
    }
}
