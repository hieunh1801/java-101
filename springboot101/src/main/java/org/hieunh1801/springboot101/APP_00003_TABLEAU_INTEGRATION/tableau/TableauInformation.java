package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class TableauInformation {

    @Value("${tableau.host}")
    public String HOST;

    @Value("${tableau.site}")
    public String SITE;

    @Value("${tableau.siteid}")
    public String SITE_ID;

    @Value("${tableau.patName}")
    public String PAT_NAME;

    @Value("${tableau.patValue}")
    public  String PAT_VALUE;

    @Value("${tableau.apiVersion}")
    public  String API_VERSION;

    public Map<String, Object> getInfo() {
        Map<String, Object> tableauInfo = new HashMap<>();
        tableauInfo.put("host", HOST);
        tableauInfo.put("site", SITE);
        tableauInfo.put("siteId", SITE_ID);
        tableauInfo.put("apiVersion", API_VERSION);
        tableauInfo.put("patName", PAT_NAME);
        tableauInfo.put("patValue", PAT_VALUE);
        return tableauInfo;
    }
}
