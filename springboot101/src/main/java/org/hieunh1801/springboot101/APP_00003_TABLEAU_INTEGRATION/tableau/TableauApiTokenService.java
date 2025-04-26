package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("tableauTokenService")
public class TableauApiTokenService {
    private Map<String, Object> serverTokenMap = new HashMap<>();
    @Autowired
    private RestTemplate restTemplate;

    public TableauApiTokenService() {
        log.info("INIT TableauApiTokenService");
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", "123kljksdf");
        tokenMap.put("tokenExpiredAt", System.currentTimeMillis() + 15 * 60 * 1000);
        serverTokenMap.put(TableauInformation.HOST, tokenMap);
    }


    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public synchronized String getToken(
            String host,
            String site,
            String siteId,
            String apiVersion,
            String patName,
            String patValue
    ) {
        Map<String, Object> serverToken = (Map<String, Object>) serverTokenMap.get(host);
        if (serverToken == null) {
            serverToken = new HashMap<>();
        }
        String token = CommonUtil.getNestedValue(serverToken, "token", null);
        long tokenExpiredAt = CommonUtil.getNestedValue(serverToken, "tokenExpiredAt", 0L);
        long now = System.currentTimeMillis();
        if (token != null && now < tokenExpiredAt) {
            return token;
        }
        log.info("REFRESH_TOKEN {}", host);

        // build header
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
        Map credentialsRes = restTemplate.postForEntity(uri, requestBody, Map.class).getBody();
        Map<String, Object> credentials = (Map<String, Object>) credentialsRes.get("credentials");
        log.info("credentials={}", credentials);
        serverToken.putAll(credentials);
        tokenExpiredAt = System.currentTimeMillis() + 15 * 60 * 1000; // expire in 15 minutes
        serverToken.put("tokenExpiredAt", tokenExpiredAt);

        serverTokenMap.put(host, serverToken);
        token = CommonUtil.getNestedValue(serverToken, "token", null);
        return token;
    }

    void resetToken(String host) {
        serverTokenMap.remove(host);
    }

}
