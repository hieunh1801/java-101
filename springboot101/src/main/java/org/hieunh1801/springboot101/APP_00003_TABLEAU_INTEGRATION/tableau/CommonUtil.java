package org.hieunh1801.springboot101.APP_00003_TABLEAU_INTEGRATION.tableau;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CommonUtil {
    /**
     * Extract a nested value from a map.
     * dataMap = {
     *     "project": {
     *         "id": 1234
     *     }
     * }
     * getNestedValue(dataMap, "project.id", 0)=1234
     * getNestedValue(dataMap, "project.test", 0)=0
     * @param dataMap
     * @param keyPath
     * @param fallback: default value
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNestedValue(Map<String, Object> dataMap, String keyPath, T fallback) {
        if (dataMap == null || keyPath == null || keyPath.isEmpty()) {
            return fallback;
        }

        String[] keys = keyPath.split("\\.");
        Object current = dataMap;

        try {
            for (String key : keys) {
                if (!(current instanceof Map)) {
                    return fallback;
                }
                current = ((Map<String, Object>) current).get(key);
                if (current == null) {
                    return fallback;
                }
            }

            return (T) current;
        } catch (Exception e) {
            return fallback;
        }
    }
}
