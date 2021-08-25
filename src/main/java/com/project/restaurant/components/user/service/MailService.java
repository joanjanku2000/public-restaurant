package com.project.restaurant.components.user.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
    void sendEmail(SimpleMailMessage simpleMailMessage);
}
