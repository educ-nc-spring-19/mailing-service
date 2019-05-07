package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateExtractor  implements Extractor {

    @Override
    public String extract(Object value) {
        if (value != null) {
            try {
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                Date date = inputFormat.parse(String.valueOf(value));
                return outputFormat.format(date);
            } catch (Exception e) {
                return "(дата не распознана)";
            }
        } else {
            return "(дата не указана)";
        }
    }

    @Override
    public String getKey() {
        return "Date";
    }
}
