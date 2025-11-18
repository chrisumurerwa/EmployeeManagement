package org.example.employee_management.Controller;

import org.example.employee_management.Dto.AuthResponse;
import org.example.employee_management.Dto.LoginRequest;
import org.example.employee_management.Dto.RegisterRequest;
import org.example.employee_management.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        AuthResponse response = userService.registerEmployee(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterRequest req) {
        AuthResponse response = userService.registerAdmin(req);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        AuthResponse response = userService.login(req);
        return ResponseEntity.ok(response);
    }
}