package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import org.springframework.stereotype.Component;

@Component
public class AddressExtractor extends DefaultExtractor {

    @Override
    public String getKey() {
        return "Address";
    }

}
