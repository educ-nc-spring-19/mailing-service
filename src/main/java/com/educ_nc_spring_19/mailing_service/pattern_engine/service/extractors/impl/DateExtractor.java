package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class DateExtractor  implements Extractor {

    @Override
    public String extract(Object value) {
        if (value != null) {
            try {
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                OffsetDateTime inputOffsetDateTime = OffsetDateTime.parse(String.valueOf(value));
                return inputOffsetDateTime.toLocalDate().format(outputFormatter);
            } catch (Exception e) {
                log.log(Level.WARN, e);
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
