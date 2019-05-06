package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class TemplateController {
    @Autowired
    private TemplateRepository templateRepository;

    @RequestMapping(value = "/template/findById", method = RequestMethod.GET, produces = "application/json")
    public Template getTemplateById(@RequestParam("id") UUID id) {
        if (templateRepository.existsById(id)) {
            return templateRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/template/getAll", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Template> getAllTemplates() {
        ArrayList<Template> result = new ArrayList<>();
        Iterable<Template> allTemplates = templateRepository.findAll();
        for (Template t : allTemplates) {
            result.add(t);
        }
        return result;
    }

    @RequestMapping(value = "/template/findByCreatorId", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Template> getTemplateByCreatorId(@RequestParam("creator_id") UUID cid) {
        if (templateRepository.existsByCreatorId(cid)) {
            return templateRepository.findByCreatorId(cid);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/template/findByType", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Template> getTemplateByType(@RequestParam("type") String type) {
        if (templateRepository.existsByType(type)) {
            return templateRepository.findByType(type);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/template/create", method = RequestMethod.POST, produces = "application/json")
    public String createTemplate(@RequestParam("creator_id") UUID cid, @RequestParam("type") String type,
                                 @RequestParam("header") String header, @RequestParam("text") String text) {
        String result = "success";
        try {
            if (templateRepository.existsByType(type)) {
                result = "Template of this type already exists";
            } else {
                Template template = new Template(cid, type, header, text);
                templateRepository.save(template);
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}