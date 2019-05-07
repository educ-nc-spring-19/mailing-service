package com.educ_nc_spring_19.mailing_service.pattern_engine.controller.request;

import lombok.Data;

import java.util.UUID;

@Data
public class TemplateRequest {

    public UUID cid;

    public String type;

    public String header;

    public String text;

}
