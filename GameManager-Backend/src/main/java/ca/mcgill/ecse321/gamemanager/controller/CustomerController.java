package ca.mcgill.ecse321.gamemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamemanager.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        String name = customerRequestDto.getName();
        String email = customerRequestDto.getEmail();
        String password = customerRequestDto.getPassword();
        Customer createdCustomer = customerService.createCustomer(name, email, password);

        return new CustomerResponseDto(createdCustomer);

    }
    @PutMapping("/customers/{email}")
    public CustomerResponseDto updateCustomer(@PathVariable String email, @RequestBody CustomerRequestDto customerRequestDto) {
        String newName = customerRequestDto.getName();
        String newPassword = customerRequestDto.getPassword();

        Customer updatedCustomer = customerService.updateCustomer(email, newName, newPassword);

        return new CustomerResponseDto(updatedCustomer);
    }
/*
    @GetMapping("/customers/login")
    public CustomerResponseDto login(@RequestParam String email, @RequestParam String password) {

    }
*/


    @GetMapping("/customers")
    public List<CustomerResponseDto> getAllCustomers() {

        Iterable<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponseDto > customerResponseDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponseDtos.add(new CustomerResponseDto(customer));

        }
        return customerResponseDtos;
    }


    @GetMapping("/customers/{email}")
    public CustomerResponseDto getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.findCustomerByEmail(email);
        return new CustomerResponseDto(customer);
    }


    @DeleteMapping("/customers/{email}")
    public void deleteCustomer(@PathVariable String email) {

        customerService.deleteCustomer(email);
    }


}
