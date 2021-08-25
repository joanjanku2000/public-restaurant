package com.project.restaurant.base.utils;

import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


@Slf4j
public class LoggedUser {
    private final UserService userServiceImpl;

    public LoggedUser(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    public User getLoggedUser() {
        log.error("Enters");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        log.info("Logged user {}", token);
        return userServiceImpl.findByEmail((String) token.getPrincipal());
    }
}
