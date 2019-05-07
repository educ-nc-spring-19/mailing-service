package com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.registry;

import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.Extractor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExtractorService {

    private static Map<String, Extractor> Extractors = new HashMap<>();

    static void registerService(String key, Extractor value) {
        Extractors.put(key, value);
    }

    public Extractor getExtractor(String key) {
        if (Extractors.containsKey(key)) {
            return Extractors.get(key);
        } else {
            return Extractors.get("Default");
        }
    }
}
