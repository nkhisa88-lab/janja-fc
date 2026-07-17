package com.team.janja_fc.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team.janja_fc.util.ActivationCodeGenerator;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ActivationCodeGenerator activationCodeGenerator;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            ActivationCodeGenerator activationCodeGenerator,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.activationCodeGenerator = activationCodeGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    public CreatePlayerResponse createPlayer(CreatePlayerRequest request) {

        String activationCode = activationCodeGenerator.generate();

        String activationCodeHash = passwordEncoder.encode(activationCode);

        userRepository.createPlayer(
                request.getFullName(),
                request.getPhoneNumber(),
                activationCodeHash);

        return new CreatePlayerResponse(activationCode);

    }

}