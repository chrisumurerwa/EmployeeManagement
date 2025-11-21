package org.example.employee_management.Service;

import jakarta.validation.Valid;
import org.example.employee_management.Dto.AuthResponse;
import org.example.employee_management.Dto.LoginRequest;
import org.example.employee_management.Dto.RegisterRequest;
import org.example.employee_management.Models.Role;
import org.example.employee_management.Models.User;
import org.example.employee_management.Repository.UserRepository;
import org.example.employee_management.Security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Role role = Role.ROLE_USER;
        if (req.getRole() != null && req.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
            role = Role.ROLE_ADMIN;
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        User saved = userRepository.save(user);
        String token = jwtUtils.generateToken(saved.getUsername(), saved.getRole().name());

        return AuthResponse.builder()
                .token(token)
//                .username(saved.getUsername())
//                .role(saved.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    // Register user as ADMIN
    @Transactional
    public AuthResponse registerAdmin(@Valid RegisterRequest req) {
        return registerUser(req, Role.ROLE_ADMIN);
    }

    // Register user as EMPLOYEE/USER
    @Transactional
    public AuthResponse registerEmployee(@Valid RegisterRequest req) {
        return registerUser(req, Role.ROLE_USER);
    }

    // Check if any admin exists in the system
    public boolean adminExists() {
        return userRepository.existsByRole(Role.ROLE_ADMIN);
    }

    // Private helper method to avoid code duplication
    private AuthResponse registerUser(RegisterRequest req, Role role) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        User saved = userRepository.save(user);
        String token = jwtUtils.generateToken(saved.getUsername(), saved.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .username(saved.getUsername())
                .role(saved.getRole().name())
                .build();
    }
}