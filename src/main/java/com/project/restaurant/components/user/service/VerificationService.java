package com.project.restaurant.components.user.service;

import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.VerificationToken;

public interface VerificationService {
    VerificationToken createVerificationToken(User user);
    VerificationToken getVerificationToken(String verificationToken);
}
