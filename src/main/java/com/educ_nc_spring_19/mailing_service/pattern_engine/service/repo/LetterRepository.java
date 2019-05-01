package com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo;

import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface LetterRepository extends CrudRepository<Letter, Long> {
    ArrayList<Letter> findById(UUID id);

    ArrayList<Letter> findByReceiverId(UUID id);

    ArrayList<Letter> findByType(String type);

    boolean existsById(UUID id);

    boolean existsByReceiverId(UUID id);

    boolean existsByType(String type);
}
