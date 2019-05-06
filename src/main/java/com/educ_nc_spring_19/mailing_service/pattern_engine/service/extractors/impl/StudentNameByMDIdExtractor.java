package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.StudentDTO;
import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class StudentNameByMDIdExtractor implements Extractor {

    @Autowired
    private MasterDataClient masterDataClient;

    @Override
    public String extract(Object value) {
        StudentDTO studentDTO = masterDataClient.getStudentById((UUID) value);
        return studentDTO.getFirstName() + " " + studentDTO.getLastName();
    }

    @Override
    public String getKey() {
        return "Student name";
    }
}
