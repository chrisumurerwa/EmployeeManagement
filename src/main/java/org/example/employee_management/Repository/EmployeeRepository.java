package org.example.employee_management.Repository;
import org.example.employee_management.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // You can add custom queries here if needed later
}
