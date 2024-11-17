package com.alex.Notes_Application_BE.utils;

import com.alex.Notes_Application_BE.models.User;
import com.alex.Notes_Application_BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthUtil {

    @Autowired
    private UserRepository userRepository;

    public Long loggedInUserId() {
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new RuntimeException("User not Found"));
        return user.getUserId();
    }

    public User loggedInUser() {
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new RuntimeException("User not Found"));
        return user;
    }



}
