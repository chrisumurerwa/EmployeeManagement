package org.example.employee_management.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.employee_management.Dto.EmployeeRequest;
import org.example.employee_management.Dto.EmployeeResponse;
import org.example.employee_management.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/test")
    public String test(){
        return "test successful";
    }

    // ONLY ADMIN CAN CREATE EMPLOYEE - Using DTO instead of Entity
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create new employee", description = "Create a new employee record (Admin only)")
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse created = employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //  FIXED: Changed from List<Employee> to List<EmployeeResponse>
    @GetMapping
    @Operation(summary = "Get all employees", description = "Get all employee records")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    //  FIXED: Changed from Employee to EmployeeResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id", description = "Get employee record by id")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    //  FIXED: Changed from Employee to EmployeeResponse
    @PutMapping("/{id}")
    @Operation(summary = "Update employee by id", description = "Update employee record by id")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    // ONLY ADMIN CAN DELETE EMPLOYEE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by id", description = "Delete employee record by id (Admin only)")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}