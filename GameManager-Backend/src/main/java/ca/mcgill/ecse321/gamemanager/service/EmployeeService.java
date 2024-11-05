package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Employee;
import ca.mcgill.ecse321.gamemanager.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    // Retrieve a employee by email (used as ID in this case)
    public Employee findEmployeeByEmail(String email) {
        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepo.findEmployeeByEmail(email));
        return optionalEmployee.orElseThrow(() -> new IllegalArgumentException("Invalid Employee email."));
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepo.findAll();
    }

    // Create a new employee
    @Transactional
    public Employee createEmployee(String name, String email, String password) {
        if (employeeRepo.findEmployeeByEmail(email) != null) {
            throw new IllegalArgumentException("A employee with this email already exists.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        Employee newEmployee = new Employee(name, email, password);
        return employeeRepo.save(newEmployee);
    }

    // Update an existing employee by email
    @Transactional
    public Employee updateEmployee(String email, String name, String newEmail, String password) {
        Employee employee = findEmployeeByEmail(email);
        
        if (name != null && !name.isBlank()) {
            employee.setName(name);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            if (!newEmail.equals(email) && employeeRepo.findEmployeeByEmail(newEmail) != null) {
                throw new IllegalArgumentException("New email is already in use by another employee.");
            }
            employee.setEmail(newEmail);
        }
        if (password != null && password.length() >= 8) {
            employee.setPassword(password);
        }

        return employeeRepo.save(employee);
    }

    // Delete a employee by email
    @Transactional
    public void deleteEmployee(String email) {
        if (employeeRepo.findEmployeeByEmail(email) == null) {
            throw new IllegalArgumentException("Employee with email " + email + " does not exist.");
        }
        employeeRepo.deleteById(email);
    }
}
