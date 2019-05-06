package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;

public class LinkExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        return "<a href=\"" + value.toString() + "\">" + getKey() + "</a>";
    }

    @Override
    public String getKey() {
        return "Accept link";
    }
}
