package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

import com.educ_nc_spring_19.mailing_service.pattern_engine.controller.request.TemplateRequest;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mailing-service/rest/api/v1/template")
public class TemplateController {

    private final TemplateRepository templateRepository;

    @GetMapping(path = "/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Template getTemplateById(@RequestParam("id") UUID id) {
        if (templateRepository.existsById(id)) {
            return templateRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Template> getAllTemplates() {
        ArrayList<Template> result = new ArrayList<>();
        Iterable<Template> allTemplates = templateRepository.findAll();
        for (Template t : allTemplates) {
            result.add(t);
        }
        return result;
    }

    @GetMapping(path = "/findByCreatorId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Template> getTemplateByCreatorId(@RequestParam("creator_id") UUID cid) {
        if (templateRepository.existsByCreatorId(cid)) {
            return templateRepository.findByCreatorId(cid);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/findByType", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Template> getTemplateByType(@RequestParam("type") String type) {
        if (templateRepository.existsByType(type)) {
            return templateRepository.findByType(type);
        } else {
            return null;
        }
    }

    @PostMapping(path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createTemplate(@RequestBody TemplateRequest templateRequest) {
        String result = "success";
        try {
            if (templateRepository.existsByType(templateRequest.type)) {
                result = "Template of this type already exists";
            } else {
                Template template = new Template(templateRequest.creator_id, templateRequest.type, templateRequest.header, templateRequest.text);
                templateRepository.save(template);
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}
