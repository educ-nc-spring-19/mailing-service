package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

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
                System.out.println("я");
                String header = renderingService.render(template.getHeader(), letterRequest.Args);
                System.out.println("почти");
                String text = renderingService.render(template.getText(), letterRequest.Args);
                System.out.println("смог");
                Letter letter = new Letter(letterRequest.receiver_id, header, text, letterRequest.type);
                System.out.println("ура!");
                String mail;
                if (letterRequest.receiver_type.equals("student")) {
                    mail = masterDataClient.getStudentById(letterRequest.receiver_id).getEmailAddress();
                } else {
                    mail = masterDataClient.getMentorByUserId(letterRequest.receiver_id).getEmailAddress();
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

}
