package com.project.restaurant.base.utils;

import org.springframework.mail.SimpleMailMessage;

public class EmailUtil {
    private EmailUtil() {
    }

    public static SimpleMailMessage getSimpleMailMessage(String to, String url) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setText("To confirm your account please click here " + url);
        email.setSubject("Confirm your account");
        return email;
    }
}
