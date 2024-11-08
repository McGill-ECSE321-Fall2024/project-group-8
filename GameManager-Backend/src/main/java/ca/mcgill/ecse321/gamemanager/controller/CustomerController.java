package ca.mcgill.ecse321.gamemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.CustomerDto;
import ca.mcgill.ecse321.gamemanager.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{email}")
    public CustomerDto findCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.findCustomerByEmail(email);
        return convertToDto(customer);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        Customer createdCustomer = customerService.createCustomer(
                customerRequestDto.getName(),
                customerRequestDto.getEmail(),
                customerRequestDto.getPassword());
        return convertToDto(createdCustomer);
    }

    @PutMapping("/{email}")
    public CustomerDto updateCustomer(@PathVariable String email, @RequestBody CustomerRequestDto customerRequestDto) {
        Customer updatedCustomer = customerService.updateCustomer(
                email,
                customerRequestDto.getName(),
                customerRequestDto.getEmail(),
                customerRequestDto.getPassword());
        return convertToDto(updatedCustomer);
    }

    @DeleteMapping("/{email}")
    public void deleteCustomer(@PathVariable String email) {
        customerService.deleteCustomer(email);
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getName(), customer.getEmail());
    }
}
