package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.exception.GameManagerException;
import ca.mcgill.ecse321.gamemanager.model.Employee;
import ca.mcgill.ecse321.gamemanager.model.Person;
import ca.mcgill.ecse321.gamemanager.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    // Retrieve a employee by email (used as ID in this case)
    public Employee findEmployeeByEmail(String email) {
        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepo.findEmployeeByEmail(email));
        return optionalEmployee.orElseThrow(() -> new GameManagerException(HttpStatus.NOT_FOUND, "Invalid Employee email."));
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepo.findAll();
    }

    public List<Employee> getAllEmployeesInAlphabet() {
        List<Employee> employees = (List<Employee>) employeeRepo.findAll();
        employees.sort(Comparator.comparing(Person::getName));
        return employees;
    }

    // Create a new employee
    @Transactional
    public Employee createEmployee(String name, String email, String password) {
        if (employeeRepo.findEmployeeByEmail(email) != null) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST,"A employee with this email already exists.");
        }
        if (password == null || password.length() < 9 || password.length() > 13) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Password length must be between 9 and 13 characters.");
        }
        Employee newEmployee = new Employee(password, name, email);
        return employeeRepo.save(newEmployee);
    }

    // Update an existing employee by email
    @Transactional
    public Employee updateEmployee(String email, String newName, String newPassword) {
        Employee employee = findEmployeeByEmail(email);
        
        if (newName == null || newName.isBlank()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Invalid Employee name.");
        }
        employee.setName(newName);

        if(newPassword == null || newPassword.length() < 9 || newPassword.length() > 13) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Password length must be between 9 and 13 characters.");
        }
        employee.setPassword(newPassword);


        return employeeRepo.save(employee);
    }

    // Delete a employee by email
    @Transactional
    public void deleteEmployee(String email) {
        Employee employee = findEmployeeByEmail(email);
        if (employee == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Employee with email " + email + " does not exist.");
        }
        employeeRepo.delete(employee);
    }
}
