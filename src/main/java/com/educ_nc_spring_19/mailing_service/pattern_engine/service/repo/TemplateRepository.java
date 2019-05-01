package com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo;

import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface TemplateRepository extends CrudRepository<Template, Long> {
    ArrayList<Template> findById(UUID id);

    ArrayList<Template> findByCreatorId(UUID id);

    ArrayList<Template> findByType(String type);

    boolean existsById(UUID id);

    boolean existsByCreatorId(UUID id);

    boolean existsByType(String type);
}
