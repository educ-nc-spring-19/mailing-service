package com.educ_nc_spring_19.mailing_service.pattern_engine.client;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.MentorDTO;
import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.StudentDTO;

import org.apache.logging.log4j.Level;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Component
public class MasterDataClient {
    private final String MASTER_DATA_URL = "127.0.0.1";
    private final String MASTER_DATA_PORT ="55000";
    private final RestTemplate restTemplate;

    public MasterDataClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public MentorDTO getMentorByUserId(UUID userId) {
        if (userId == null) {
            log.log(Level.WARN, "userId is null");
            return null;
        }

        ResponseEntity<List<MentorDTO>> response = restTemplate.exchange(
                UriComponentsBuilder.newInstance().scheme("http").host(MASTER_DATA_URL).port(MASTER_DATA_PORT)
                        .path("/master-data/rest/api/v1/mentor")
                        .queryParam("userId",userId.toString())
                        .build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MentorDTO>>(){});

        log.log(Level.INFO, "getMentorByUserId, response status = " + response.getStatusCode().toString());
        if (response.getStatusCode().equals(HttpStatus.OK) && CollectionUtils.isNotEmpty(response.getBody())) {
            log.log(Level.INFO, "Mentors: " + response.getBody().stream().map(MentorDTO::getId).collect(Collectors.toList()));
            return response.getBody().get(0);
        }

        return null;
    }


    public StudentDTO getStudentById(UUID studentId) {
        ResponseEntity<StudentDTO> response = restTemplate.getForEntity(
                UriComponentsBuilder.newInstance().scheme("http").host(MASTER_DATA_URL).port(MASTER_DATA_PORT)
                        .path("/master-data/rest/api/v1/student/{id}")
                        .buildAndExpand(studentId.toString()).toUri(),
                StudentDTO.class
        );

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        log.log(Level.WARN, "studentId: \'" + studentId.toString()
                + "\', statusCode: \'" + response.getStatusCode().toString() + "\'");
        return null;
    }

    public MentorDTO getMentorById(UUID mentorId) {
        ResponseEntity<MentorDTO> response = restTemplate.getForEntity(
                UriComponentsBuilder.newInstance().scheme("http").host(MASTER_DATA_URL).port(MASTER_DATA_PORT)
                        .path("/master-data/rest/api/v1/mentor/{id}")
                        .buildAndExpand(mentorId.toString()).toUri(),
                MentorDTO.class
        );

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        log.log(Level.WARN, "mentorId: \'" + mentorId.toString()
                + "\', statusCode: \'" + response.getStatusCode().toString() + "\'");
        return null;
    }

}
