package com.team.janja_fc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.team.janja_fc.security.user.JanjaUserDetails;
import com.team.janja_fc.user.User;

@Service
public class CurrentUserService {

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof JanjaUserDetails userDetails) {
            return userDetails.getUser();
        }

        return null;
    }
}