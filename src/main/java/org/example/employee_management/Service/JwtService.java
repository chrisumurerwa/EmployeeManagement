package org.example.employee_management.Service;



import org.example.employee_management.Security.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtils jwtUtils;

    public JwtService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generateToken(String username, String role) {
        return jwtUtils.generateToken(username, role);
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    public String extractUsername(String token) {
        return jwtUtils.getUsernameFromToken(token);
    }

    public String extractRole(String token) {
        return jwtUtils.getRoleFromToken(token);
    }
}
