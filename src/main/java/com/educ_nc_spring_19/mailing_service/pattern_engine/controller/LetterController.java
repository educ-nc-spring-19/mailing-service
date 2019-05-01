package com.educ_nc_spring_19.mailing_service.pattern_engine.controller;

import com.educ_nc_spring_19.mailing_service.notificator.LetterSender;
import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Template;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.JSONArgsParsingService;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.LetterRepository;
import com.educ_nc_spring_19.mailing_service.pattern_engine.service.repo.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class LetterController {
    @Autowired
    private LetterRepository letterRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private JSONArgsParsingService jsonArgsParsingService;
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

    //Передаваемые данные должны быть в формате json, в места, где нужно имя получателя, вписывается пустое место
    //и в аргументах передаётся id получателя, чтобы я потом могла подтянуть имя получателя из Master Data и вставить
    //в местах, где нужно имя получателя, ставится параметр name: ""
    //в местах, где нужно имя куратора, ставится параметр mentor_name: "UUID ментора"
    //в местах, где нужно имя бэкапа, ставится параметр backup_name: "UUID бэкапа"
    //ВСЕ АРГУМЕНТЫ ДОЛЖНЫ БЫТЬ СТРОКОВЫМИ
    //id получателя - id пользователя, id ментора или бэкапа - id ментора из мастердаты, НЕ ПОЛЬЗОВАТЕЛЯ
    //receiver_type - это student или mentor
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST, produces = "application/json")
    public String sendMail(@RequestParam("receiver_id") UUID rid, @RequestParam("receiver_type") String rtype,
                           @RequestParam("type") String type, @RequestParam("header_args") String hArgs,
                           @RequestParam("text_args") String tArgs) {
        String result = "success";
        if (templateRepository.existsByType(type)) {
            try {
                Template template = templateRepository.findByType(type).get(0);
                ArrayList<String> headerArgs = jsonArgsParsingService.Parse(rtype, hArgs, rid);
                ArrayList<String> textArgs = jsonArgsParsingService.Parse(rtype, tArgs, rid);
                Letter letter = new Letter(rid, template.buildText(textArgs), template.buildHeader(headerArgs), type);
                String mail;

                if (rtype == "student") {
                    mail = masterDataClient.getStudentById(rid);
                    //TODO!!!!!!!!!!!!!!!!!!!!!!!!
                } else {
                    mail = masterDataClient.getMentorById(rid);
                    //TODO!!!!!!!!!!!!!!!!!!!!!!!!
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

}
