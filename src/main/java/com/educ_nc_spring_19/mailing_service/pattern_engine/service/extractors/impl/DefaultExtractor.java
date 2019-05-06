package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;

public class DefaultExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        return value.toString();
    }

    @Override
    public String getKey() {
        return "Default";
    }
}
