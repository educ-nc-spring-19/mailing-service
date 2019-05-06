package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;

public class AddressExtractor extends DefaultExtractor {

    @Override
    public String getKey() {
        return "Address";
    }

}
