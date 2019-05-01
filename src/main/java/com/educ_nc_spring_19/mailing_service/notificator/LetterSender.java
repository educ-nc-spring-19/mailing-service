package com.educ_nc_spring_19.mailing_service.notificator;

import com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LetterSender {
    private static final String DEFAULT_SENDER = MyConstants.MY_EMAIL;
    @Autowired
    public JavaMailSender emailSender;

    public void SendLetter (Letter letter, String receiverMail, String senderMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderMail);
        message.setTo(receiverMail);
        message.setSubject(letter.getHeader());
        message.setText(letter.getText());

        this.emailSender.send(message);
    }

    public void SendLetter (Letter letter, String receiverMail) {
        SendLetter(letter, receiverMail, DEFAULT_SENDER);

    }
}
