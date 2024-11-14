package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.EmployeeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Employee;
import ca.mcgill.ecse321.gamemanager.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{email}")
    public EmployeeResponseDto findEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findEmployeeByEmail(email);
        return new EmployeeResponseDto(employee);
    }

    @GetMapping
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeResponseDto> employeesDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeesDtos.add(new EmployeeResponseDto(employee));
        }
        return employeesDtos;
    }

    @PostMapping
    public EmployeeResponseDto createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee createdEmployee = employeeService.createEmployee(
                employeeRequestDto.getName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getPassword());
        return new EmployeeResponseDto(createdEmployee);
    }

    @PutMapping("/{email}")
    public EmployeeResponseDto updateEmployee(@PathVariable String email, @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee updatedEmployee = employeeService.updateEmployee(
                email,
                employeeRequestDto.getName(),
                employeeRequestDto.getPassword());
        return new EmployeeResponseDto(updatedEmployee);
    }

    @DeleteMapping("/{email}")
    public void deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
    }


}
