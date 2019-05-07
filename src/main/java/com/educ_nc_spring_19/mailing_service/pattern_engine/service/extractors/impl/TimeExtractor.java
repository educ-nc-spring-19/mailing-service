package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        if (value != null) {
            try {
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                Date date = inputFormat.parse(String.valueOf(value));
                return outputFormat.format(date);
            } catch (Exception e) {
                return "(время не распознано)";
            }
        } else {
            return "(время не указано)";
        }
    }

    @Override
    public String getKey() {
        return "Time";
    }
}
