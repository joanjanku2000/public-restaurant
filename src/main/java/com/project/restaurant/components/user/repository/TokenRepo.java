package com.project.restaurant.components.user.repository;


import com.project.restaurant.components.user.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    List<VerificationToken> findAllByUserId(Long userId);
}
