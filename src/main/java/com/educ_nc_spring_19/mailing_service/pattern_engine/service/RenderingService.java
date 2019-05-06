package com.educ_nc_spring_19.mailing_service.pattern_engine.service;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.registry.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RenderingService {

    @Autowired
    private ExtractorService extractorService;

    public String render(final String template, Map<String, Object> params) {
        params.forEach((key, value) -> {
            template.replaceAll("%" + key + "%", renderPlaceholder(key, value));
        });
        return template;
    }

    private String renderPlaceholder(String key, Object value) {
        return extractorService.getExtractor(key).extract(value);
    }
}
