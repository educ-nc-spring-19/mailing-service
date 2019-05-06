package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors;

public interface Extractor {

    public String extract(Object value);

    public String getKey();

}
