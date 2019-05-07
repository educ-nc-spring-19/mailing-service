package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.stereotype.Component;

@Component
public class LinkExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        if (value != null) {
            return String.valueOf(value);
        } else {
            return "(нет ссылки)";
        }
    }

    @Override
    public String getKey() {
        return "Accept link";
    }
}
