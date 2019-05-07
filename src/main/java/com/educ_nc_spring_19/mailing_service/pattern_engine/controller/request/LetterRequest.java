package com.educ_nc_spring_19.mailing_service.pattern_engine.controller.request;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class LetterRequest {
    public UUID rid;

    public String rtype;

    public String type;

    public Map<String, Object> Args;
}
