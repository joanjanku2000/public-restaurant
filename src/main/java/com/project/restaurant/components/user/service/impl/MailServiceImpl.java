package com.project.restaurant.components.user.service.impl;

import com.project.restaurant.components.user.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        log.info("sending mail to {} from {}", simpleMailMessage.getTo(), simpleMailMessage.getFrom());
        javaMailSender.send(simpleMailMessage);
    }
}
