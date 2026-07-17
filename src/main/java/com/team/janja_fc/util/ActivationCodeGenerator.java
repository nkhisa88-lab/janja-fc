package com.team.janja_fc.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class ActivationCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate() {

        int code = 100000 + RANDOM.nextInt(900000);

        return String.valueOf(code);

    }

}