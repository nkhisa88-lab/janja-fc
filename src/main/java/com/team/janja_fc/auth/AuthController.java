package com.team.janja_fc.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);

    }

    @PostMapping("/set-password")
    public LoginResponse setPassword(
            @RequestHeader("Authorization") String authorization,
            @RequestBody SetPasswordRequest request) {

        String accessToken = authService.setPassword(
                authorization,
                request);

        if (accessToken == null) {
            return new LoginResponse(
                    false,
                    false,
                    null);
        }

        return new LoginResponse(
                true,
                false,
                accessToken);
    }

}