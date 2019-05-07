package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

//import org.springframework.context.annotation.Bean;

public class AddressExtractor extends DefaultExtractor {

    /*
    @Bean
    public AddressExtractor AddressExtractor() {
        return new AddressExtractor();
    }
    */

    public AddressExtractor() {}

    @Override
    public String getKey() {
        return "Address";
    }

}
