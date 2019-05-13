package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class TimeExtractor implements Extractor {

    @Override
    public String extract(Object value) {
        if (value != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                OffsetDateTime inputOffsetDateTime = OffsetDateTime.parse(String.valueOf(value));
                return inputOffsetDateTime.toLocalTime().format(formatter);
            } catch (Exception e) {
                log.log(Level.WARN, e);
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
