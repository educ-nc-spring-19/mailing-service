package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

import com.educ_nc_spring_19.mailing_service.notificator.LetterSender;
import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import com.educ_nc_spring_19.mailing_service.pattern_engine.controller.request.LetterRequest;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.RenderingService;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.LetterRepository;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.TemplateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/mailing-service/rest/api/v1/letter")
public class LetterController {
    private final LetterRepository letterRepository;
    private final TemplateRepository templateRepository;
    private final RenderingService renderingService;
    private final LetterSender letterSender;
    private final MasterDataClient masterDataClient;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Letter getLetterById(@RequestParam("id") UUID id) {
        if (letterRepository.existsById(id)) {
            return letterRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Letter> getAllLetters() {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findAll();
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }

    @GetMapping(path = "/findByReceiverId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Letter> getLettersByReceiverId(@RequestParam("id") UUID rid) {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findByReceiverId(rid);
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }

    @GetMapping(path = "/findByType", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Letter> getLettersByType(@RequestParam("type") String type) {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findByType(type);
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }


    //receiver_type - это student или mentor
    @PostMapping(path = "/send",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity send(@RequestBody LetterRequest letterRequest) {
        String mailReceiver;
        if (templateRepository.existsByType(letterRequest.getType())) {
            try {
                Template template = templateRepository.findByType(letterRequest.getType()).get(0);
                System.out.println("я");
                String header = renderingService.render(template.getHeader(), letterRequest.Args);
                System.out.println("почти");
                String text = renderingService.render(template.getText(), letterRequest.Args);
                System.out.println("смог");
                Letter letter = new Letter(letterRequest.receiver_id, header, text, letterRequest.getType());
                System.out.println("ура!");
                if (letterRequest.receiver_type.equals("student")) {
                    mailReceiver = masterDataClient.getStudentById(letterRequest.receiver_id).getEmailAddress();
                } else {
                    mailReceiver = masterDataClient.getMentorByUserId(letterRequest.receiver_id).getEmailAddress();
                }
                letterSender.sendLetter(letter, mailReceiver);
                letterRepository.save(letter);
            } catch (Exception e) {
                log.log(Level.WARN, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(objectMapper.createObjectNode()
                            .put("message", "Template with type '" + letterRequest.getType() + "' doesn't exist")
                    );
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapper.createObjectNode().put("message", "Mail successfully sent to " + mailReceiver));
    }

}
