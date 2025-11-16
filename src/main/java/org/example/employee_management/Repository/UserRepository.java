package org.example.employee_management.Repository;

import org.example.employee_management.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username or email for authentication
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // Check if username or email already exists (for validation)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
