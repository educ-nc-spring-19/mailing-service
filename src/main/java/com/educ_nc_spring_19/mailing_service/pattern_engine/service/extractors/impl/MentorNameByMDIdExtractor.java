package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.MentorDTO;
import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MentorNameByMDIdExtractor implements Extractor {

    @Autowired
    private MasterDataClient masterDataClient;

    @Override
    public String extract(Object value) {
        if (value != null) {
            MentorDTO mentorDTO = masterDataClient.getMentorById(UUID.fromString(String.valueOf(value)));
            return mentorDTO.getFirstName() + " " + mentorDTO.getLastName();
        } else {
            return "(не указан)";
        }
    }

    @Override
    public String getKey() {
        return "Mentor name";
    }

}
