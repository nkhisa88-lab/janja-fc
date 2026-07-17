package com.team.janja_fc.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team.janja_fc.security.JwtService;
import com.team.janja_fc.user.User;
import com.team.janja_fc.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        System.out.println("=================================");
        System.out.println("LOGIN ATTEMPT");
        System.out.println("Phone Number: " + request.getPhoneNumber());

        User user = userRepository.findByPhoneNumber(
                request.getPhoneNumber());

        System.out.println("User Found: " + (user != null));

        if (user == null) {
            System.out.println("Reason: User not found");
            return new LoginResponse(false, false, null);
        }

        System.out.println("Needs Activation: " + user.needsActivation());

        boolean valid;

        if (user.needsActivation()) {

            valid = passwordEncoder.matches(
                    request.getSecret(),
                    user.activationCodeHash());

            System.out.println("Activation Code Valid: " + valid);

            if (!valid) {
                System.out.println("Reason: Wrong activation code");
                return new LoginResponse(false, false, null);
            }

            String token = jwtService.generateActivationToken(user);

            System.out.println("Activation Token Generated");

            return new LoginResponse(
                    true,
                    true,
                    token);

        }

        valid = passwordEncoder.matches(
                request.getSecret(),
                user.getPasswordHash());

        System.out.println("Password Valid: " + valid);

        if (!valid) {
            System.out.println("Reason: Wrong password");
            return new LoginResponse(false, false, null);
        }

        String token = jwtService.generateAccessToken(user);

        System.out.println("Access Token Generated");
        System.out.println(token);
        System.out.println("=================================");

        return new LoginResponse(
                true,
                false,
                token);

    }

    public boolean setPassword(
            String authorization,
            SetPasswordRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return false;
        }

        String token = authorization.substring(7);

        if (!jwtService.isValid(token)) {
            return false;
        }

        if (jwtService.extractTokenType(token) != TokenType.ACTIVATION) {
            return false;
        }

        String phoneNumber = jwtService.extractPhoneNumber(token);

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            return false;
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        userRepository.activateUser(
                user.getPhoneNumber(),
                passwordHash);

        return true;

    }

}