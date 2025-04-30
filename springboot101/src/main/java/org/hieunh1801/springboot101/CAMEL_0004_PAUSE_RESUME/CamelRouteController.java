package org.hieunh1801.springboot101.CAMEL_0004_PAUSE_RESUME;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camel/routes")
public class CamelRouteController {
    @Autowired
    private CamelContext camelContext;

    @GetMapping
    public List<Map<String, String>> getAllRoutes() {
        return camelContext.getRoutes().stream()
                .map(route -> {
                    String id = route.getId();
                    String status = camelContext.getRouteController().getRouteStatus(id).name();
                    Map<String, String> routeInfo = new HashMap<>();
                    routeInfo.put("id", id);
                    routeInfo.put("status", status);
                    return routeInfo;
                })
                .collect(Collectors.toList());
    }
}
