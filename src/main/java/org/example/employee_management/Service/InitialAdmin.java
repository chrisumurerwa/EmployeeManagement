package org.example.employee_management.Service;

import org.example.employee_management.Models.Role;
import org.example.employee_management.Models.User;
import org.example.employee_management.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitialAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByEmail("nkerenke@gmail.com")) {

            User admin = User.builder()
                    .username("nkerenke")
                    .email("nkerenke@gmail.com")
                    .password(passwordEncoder.encode("nkerenke123"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userRepository.save(admin);

            System.out.println("====================================");
            System.out.println(" DEFAULT ADMIN CREATED ");
            System.out.println("Username: nkerenke");
            System.out.println("Password: nkerenke123");
            System.out.println("Role: ROLE_ADMIN");
            System.out.println("====================================");
        }
    }
}   // ✔️ This closing brace was missing
