package org.example.employee_management.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank
    private String username;


    @Email
    @NotBlank
    private String email;


    @NotBlank
    @Size(min = 6)
    private String password;


    // optional: accept ROLE_USER or ROLE_ADMIN
    private String role;
}
