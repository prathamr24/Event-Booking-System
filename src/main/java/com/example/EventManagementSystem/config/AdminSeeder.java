package com.example.EventManagementSystem.config;

import com.example.EventManagementSystem.entity.Role;
import com.example.EventManagementSystem.entity.User;
import com.example.EventManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@bookmyevent.com")) {

            User admin = User.builder()
                    .name("Pratham Relan")
                    .email("admin@bookmyevent.com")
                    .password(
                            passwordEncoder.encode("Pratham@123")
                    )
                    .phone("9999999999")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);

            System.out.println("Admin User Created");
        }
    }
}
