package com.project.restaurant.components.user.repository;

import com.project.restaurant.components.user.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByEmail(String email);
    Optional<UserDetails> findByEmailAndUserEnabled(String email, Boolean enabled);
}
