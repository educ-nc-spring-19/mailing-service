package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.impl;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;

import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.time.format.FormatStyle;

public class DateExtractor  implements Extractor {
    @Override
    public String extract(Object value) {
        return ((OffsetDateTime) value).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    @Override
    public String getKey() {
        return "Date";
    }
}
