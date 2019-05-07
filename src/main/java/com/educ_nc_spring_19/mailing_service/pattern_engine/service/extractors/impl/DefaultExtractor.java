package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.stereotype.Component;

@Component
public class DefaultExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        return String.valueOf(value);
    }

    @Override
    public String getKey() {
        return "Default";
    }
}
