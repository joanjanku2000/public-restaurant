package com.project.restaurant.components.user.service.impl;

import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.VerificationToken;
import com.project.restaurant.components.user.repository.TokenRepo;
import com.project.restaurant.components.user.service.VerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private TokenRepo tokenRepo;

    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        log.info("Just created verification token: {}", verificationToken);
        tokenRepo.save(verificationToken);
        return verificationToken;
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepo.findByToken(verificationToken);
    }
}
