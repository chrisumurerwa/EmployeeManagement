package org.example.employee_management.Service;

import org.example.employee_management.Dto.EmployeeRequest;
import org.example.employee_management.Dto.EmployeeResponse;
import org.example.employee_management.Models.Employee;
import org.example.employee_management.Repository.EmployeeRepository;
import org.example.employee_management.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ✅ CORRECTED: Convert EmployeeRequest to Employee entity before saving
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        // Convert EmployeeRequest DTO to Employee entity
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setPosition(employeeRequest.getPosition());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setHireDate(employeeRequest.getHireDate());

        // Save the Employee entity (not the DTO)
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert back to EmployeeResponse DTO
        return convertToResponse(savedEmployee);
    }

    // ✅ CORRECTED: Return List<EmployeeResponse> instead of List<Employee>
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ✅ CORRECTED: Return EmployeeResponse instead of Employee
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return convertToResponse(employee);
    }

    // ✅ CORRECTED: Return EmployeeResponse instead of Employee
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest updatedEmployee) {
        Employee existingEmployee = getEmployeeByIdEntity(id); // Use a separate method to get the entity

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setPosition(updatedEmployee.getPosition());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setHireDate(updatedEmployee.getHireDate());
        // Remove this line if you don't want manager in Swagger:
        // existingEmployee.setManager(updatedEmployee.getManager());

        Employee savedEmployee = employeeRepository.save(existingEmployee);
        return convertToResponse(savedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee existingEmployee = getEmployeeByIdEntity(id);
        employeeRepository.delete(existingEmployee);
    }

    // Helper method to get Employee entity (for internal use)
    private Employee getEmployeeByIdEntity(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    // Helper method to convert Employee entity to EmployeeResponse DTO
    private EmployeeResponse convertToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setPosition(employee.getPosition());
        response.setDepartment(employee.getDepartment());
        response.setHireDate(employee.getHireDate());
        return response;
    }
}