package com.team.janja_fc.bootstrap;

import com.team.janja_fc.config.AdminProperties;
import com.team.janja_fc.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminProperties adminProperties;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(
            UserRepository userRepository,
            AdminProperties adminProperties,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminProperties = adminProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.adminExists()) {
            System.out.println("Admin already exists.");
            return;
        }

        String hashedCode = passwordEncoder.encode(
                adminProperties.getActivationCode());

        userRepository.createAdmin(
                "Super Admin",
                "0712345678",
                hashedCode);

        System.out.println("=================================");
        System.out.println("Admin created successfully!");
        System.out.println("Phone : 0712345678");
        System.out.println("Activation Code : (from Windows environment)");
        System.out.println("=================================");
    }
}