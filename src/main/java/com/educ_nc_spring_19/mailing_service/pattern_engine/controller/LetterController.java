package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.MentorDTO;
import com.educ_nc_spring_19.educ_nc_spring_19_common.common.dto.StudentDTO;
import com.educ_nc_spring_19.mailing_service.notificator.LetterSender;
import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import com.educ_nc_spring_19.mailing_service.pattern_engine.controller.request.LetterRequest;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.RenderingService;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.extractors.registry.ExtractorService;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.LetterRepository;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class LetterController {
    @Autowired
    private LetterRepository letterRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private RenderingService renderingService;
    @Autowired
    private LetterSender letterSender;
    @Autowired
    private MasterDataClient masterDataClient;
    @Autowired
    private ExtractorService extractorService;

    @RequestMapping(value = "/letter/findById", method = RequestMethod.GET, produces = "application/json")
    public Letter getLetterById(@RequestParam("id") UUID id) {
        if (letterRepository.existsById(id)) {
            return letterRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/letter/getAll", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Letter> getAllLetters() {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findAll();
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }

    @RequestMapping(value = "/letter/findByReceiverId", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Letter> getLettersByReceiverId(@RequestParam("id") UUID rid) {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findByReceiverId(rid);
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }

    @RequestMapping(value = "/letter/findByType", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Letter> getLettersByType(@RequestParam("type") String type) {
        ArrayList<Letter> result = new ArrayList<>();
        Iterable<Letter> allLetters = letterRepository.findByType(type);
        for (Letter l : allLetters) {
            result.add(l);
        }
        return result;
    }


    //receiver_type - это student или mentor
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String sendMail(@RequestBody LetterRequest letterRequest) {
        String result = "success";
        if (templateRepository.existsByType(letterRequest.type)) {
            try {
                Template template = templateRepository.findByType(letterRequest.type).get(0);
                String header = renderingService.render(template.getHeader(), letterRequest.Args);
                String text = renderingService.render(template.getText(), letterRequest.Args);
                Letter letter = new Letter(letterRequest.rid, header, text, letterRequest.type);
                String mail;
                if (letterRequest.rtype.equals("student")) {
                    mail = masterDataClient.getStudentById(letterRequest.rid).getEmailAddress();
                } else {
                    mail = masterDataClient.getMentorByUserId(letterRequest.rid).getEmailAddress();
                }
                letterSender.SendLetter(letter, mail);
                letterRepository.save(letter);
            } catch (Exception e) {
                result = "Exception: " + e.getMessage();
            }
        } else {
            result = "This template type doesn't exist";
        }
        return result;
    }

    @RequestMapping(value = "/letter/test", method = RequestMethod.GET, produces = "application/json")
    public String Test() {
        String result = "success";
        try {
            Letter letter = new Letter(UUID.randomUUID(), "TEST!", "Test message", "test");
            letterSender.SendLetter(letter, "zetesovadana@gmail.com");
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    @RequestMapping(value = "/mdStudent/test", method = RequestMethod.GET, produces = "application/json")
    public StudentDTO TestStudent() {
        return masterDataClient.getStudentById(UUID.fromString("97ee68a6-2993-49cd-926b-887911876457"));
    }

    @RequestMapping(value = "/mdMentor/test", method = RequestMethod.GET, produces = "application/json")
    public MentorDTO TestMentor(@RequestParam("id") UUID id) {
        return masterDataClient.getMentorByUserId(id);
    }

    @RequestMapping(value = "/extractors/test", method = RequestMethod.GET, produces = "application/json")
    public String TestExtractors() {
        return extractorService.getExtractor("Mentor name").getKey();
    }

}
