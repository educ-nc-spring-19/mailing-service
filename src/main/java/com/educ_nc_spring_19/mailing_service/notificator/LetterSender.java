package com.educ_nc_spring_19.mailing_service.notificator;

import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LetterSender {
    private static final String DEFAULT_SENDER = MyConstants.MY_EMAIL;
    private final JavaMailSender emailSender;

    public void sendLetter(Letter letter, String mailReceiver, String mailSender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender);
        message.setTo(mailReceiver);
        message.setSubject(letter.getHeader());
        message.setText(letter.getText());

        emailSender.send(message);
    }

    public void sendLetter(Letter letter, String mailReceiver) {
        sendLetter(letter, mailReceiver, DEFAULT_SENDER);
    }
}
