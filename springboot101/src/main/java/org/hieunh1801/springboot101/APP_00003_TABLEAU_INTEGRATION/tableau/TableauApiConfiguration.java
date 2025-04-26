package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TableauApiConfiguration {
    @Autowired
    private TableauApiTokenService tokenService;

    @Bean
    public RequestInterceptor tableauRequestInterceptor() {
        return requestTemplate -> {

            Map<String, Collection<String>> headers = requestTemplate.headers();
            String host = extractHeaderValue(headers, "host");
            String site = extractHeaderValue(headers, "site");
            String siteId = extractHeaderValue(headers, "siteId");
            String apiVersion = extractHeaderValue(headers, "apiVersion");
            String patName = extractHeaderValue(headers, "patName");
            String patValue = extractHeaderValue(headers, "patValue");
            String token = tokenService.getToken(host, site, siteId, apiVersion, patName, patValue);
            log.info("INTERCEPTOR: {} {}...", host, token.substring(0, 10));

            requestTemplate.removeHeader("site");
            requestTemplate.removeHeader("siteId");
            requestTemplate.removeHeader("apiVersion");
            requestTemplate.removeHeader("patName");
            requestTemplate.removeHeader("patValue");

            requestTemplate.header("X-Tableau-Auth", token);
        };
    }

    private String extractHeaderValue(Map<String, Collection<String>> headers, String headerName) {
        Collection<String> headerValue = headers.get(headerName);
        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }
        return headerValue.iterator().next();
    }

    @Bean
    public ErrorDecoder errorDecoder(TableauApiTokenService tableauTokenService) {
        return (s, response) -> {
            int status = response.status();
            String reason = response.reason();
            RequestTemplate requestTemplate = response.request().requestTemplate();
            Map<String, Collection<String>> headers = requestTemplate.headers();
            String host = extractHeaderValue(headers, "host");
            log.error("Feign Error - Method: {}, Status: {}, Reason: {}", s, status, reason);
            if (status == 401) {
                tableauTokenService.resetToken(host);
            }
            throw new RuntimeException("Error occurred while processing request" + "\n" + reason);
        };
    }
}
