package com.boz.control;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueryDslMappingsBase {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public Map<String, Class<?>> querydslHttpRequestContextAwareServletFilterMappings() {
        Map<String, Class<?>> mappings = new HashMap<>();
        //mappings.put(contextPath+"/sample/parameter_group", ParameterGroup.class);
        return mappings;
    }

}
