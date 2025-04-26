package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import java.util.HashMap;
import java.util.Map;

public class TableauInformation {
    public static final String HOST = "";
    public static final String SITE = "";
    public static final String PAT_NAME = "";
    public static final String PAT_VALUE = "";
    public static final String API_VERSION = "";
    public static final String SITE_ID = "";

    public static Map<String, Object> getInfo() {
        Map<String, Object> tableauInfo = new HashMap<>();
        tableauInfo.put("host", TableauInformation.HOST);
        tableauInfo.put("site", TableauInformation.SITE);
        tableauInfo.put("siteId", TableauInformation.SITE_ID);
        tableauInfo.put("apiVersion", TableauInformation.API_VERSION);
        tableauInfo.put("patName", TableauInformation.PAT_NAME);
        tableauInfo.put("patValue", TableauInformation.PAT_VALUE);
        return tableauInfo;
    }
}
