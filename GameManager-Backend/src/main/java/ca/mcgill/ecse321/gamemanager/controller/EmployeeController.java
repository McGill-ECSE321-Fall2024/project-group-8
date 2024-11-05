package ca.mcgill.ecse321.gamemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.EmployeeDto;
import ca.mcgill.ecse321.gamemanager.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Employee;
import ca.mcgill.ecse321.gamemanager.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{email}")
    public EmployeeDto findEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findEmployeeByEmail(email);
        return convertToDto(employee);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee createdEmployee = employeeService.createEmployee(
                employeeRequestDto.getName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getPassword());
        return convertToDto(createdEmployee);
    }

    @PutMapping("/{email}")
    public EmployeeDto updateEmployee(@PathVariable String email, @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee updatedEmployee = employeeService.updateEmployee(
                email,
                employeeRequestDto.getName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getPassword());
        return convertToDto(updatedEmployee);
    }

    @DeleteMapping("/{email}")
    public void deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
    }

    private EmployeeDto convertToDto(Employee employee) {
        return new EmployeeDto(employee.getName(), employee.getEmail());
    }
}
