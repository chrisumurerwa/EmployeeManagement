package org.example.employee_management.Controller;

import io.swagger.v3.oas.annotations.Operation;
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


@Operation(summary = "Register User", description = "Register User")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        AuthResponse response = userService.registerEmployee(req);
        return ResponseEntity.ok(response);
    }



@Operation(summary = "Login by User and Admin", description = "Login  by user and Admin")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        AuthResponse response = userService.login(req);
        return ResponseEntity.ok(response);
    }
}