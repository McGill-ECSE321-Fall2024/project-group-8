package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.gamemanager.dto.LoginResponse;
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

    @PostMapping("/employees/login")
    public LoginResponse login(@RequestBody EmployeeRequestDto employeeRequestDto) {
        String email = employeeRequestDto.getEmail();
        String password = employeeRequestDto.getPassword();
        LoginResponse response = new LoginResponse();
        if (employeeService.loginEmployee(email,password) != null){
            response.setSuccess(true);
            response.setMessage("Successfully logged in");
            response.setUserEmail(email);
        }
        return response;
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

    @GetMapping("/isEmployee/{email}")
    public boolean isEmployeeExist(@PathVariable String email) {
        return employeeService.isEmployeeExist(email);
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
