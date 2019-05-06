package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

public class BackupNameByMDIdExtractor extends MentorNameByMDIdExtractor{

    @Override
    public String getKey() {
        return "Backup name";
    }
}
