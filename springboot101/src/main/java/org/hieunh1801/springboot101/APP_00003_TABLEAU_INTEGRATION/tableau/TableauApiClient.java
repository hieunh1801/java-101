package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;


@FeignClient(name = "tableau-api", url = "https://t.tableau-report.com", configuration = TableauApiConfiguration.class)
public interface TableauApiClient {
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> signIn(
            URI baseUri,
            @RequestHeader(name = "Content-Type") String contentTypeHeader,
            @RequestHeader(name = "Accept") String acceptHeader,
            @RequestBody Map<String, Object> payload
    );

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> signOut(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> get(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> get(
            @RequestHeader(name = "tableauInfo") Map<String, Object> tableauInfo,
            URI baseUri);

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> post(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token,
            @RequestHeader(name = "Content-Type", required = true) String contentTypeHeader,
            @RequestHeader(name = "Accept", required = true) String acceptHeader,
            @RequestBody Map<String, Object> payload
    );

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> put(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token,
            @RequestHeader(name = "Content-Type", required = true) String contentTypeHeader,
            @RequestHeader(name = "Accept", required = true) String acceptHeader,
            @RequestBody Map<String, Object> payload
    );

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> getProjects(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> createProject(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token,
            @RequestHeader(name = "Content-Type", required = true) String contentTypeHeader,
            @RequestHeader(name = "Accept", required = true) String acceptHeader,
            @RequestBody Map<String, Object> payload
    );

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> getSites(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> getResources(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    LinkedHashMap<String, Object> initFileUpload(
            URI baseUri,
            @RequestHeader(name = "X-Tableau-Auth", required = true) String token
    );
}