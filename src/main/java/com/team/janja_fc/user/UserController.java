package com.team.janja_fc.user;

import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/players")
    public CreatePlayerResponse createPlayer(
            @RequestBody CreatePlayerRequest request) {

        return userService.createPlayer(request);

    }

}