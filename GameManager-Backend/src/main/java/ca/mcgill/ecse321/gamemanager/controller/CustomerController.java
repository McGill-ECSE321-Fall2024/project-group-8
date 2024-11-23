package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.*;
import ca.mcgill.ecse321.gamemanager.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

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
    @PutMapping("/customers/addCart/{gId}")
    public CustomerResponseDto addCart(@PathVariable int gId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.addInCart(gId, email);
        return new CustomerResponseDto(customer);
    }
    @PutMapping("/customers/removeInCart/{gId}")
    public CustomerResponseDto removeInCart(@PathVariable int gId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.removeInCart(gId, email);
        return new CustomerResponseDto(customer);
    }

    @GetMapping("/customers/{email}/cartAll")
    public GameListDto getAllInCart(@PathVariable String email){
        List<Game> games = customerService.getInCart(email);
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            GameDto gameListDto = new GameDto(game);
            gameDtos.add(gameListDto);
        }
        return new GameListDto(gameDtos);
    }

    @PutMapping("/customers/addWishList/{gId}")
    public CustomerResponseDto addWishList(@PathVariable int gId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.addInWishlist(gId, email);
        return new CustomerResponseDto(customer);
    }
    @PutMapping("/customers/removeWishList/{gId}")
    public CustomerResponseDto removeWishList(@PathVariable int gId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.addInWishlist(gId, email);
        return new CustomerResponseDto(customer);
    }

    @PutMapping("/customers/addOrder/{OrderId}")
    public CustomerResponseDto addOrder(@PathVariable int OrderId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.addPurchaseOrder(OrderId, email);
        return new CustomerResponseDto(customer);
    }
    @PutMapping("/customer/removeOrder/{OrderId}")
    public CustomerResponseDto removeOrder(@PathVariable int OrderId, @RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        Customer customer = customerService.removePurchaseOrder(OrderId, email);
        return new CustomerResponseDto(customer);
    }

    @PostMapping("/customers/login")
    public LoginResponse login(@RequestBody CustomerRequestDto customerRequestDto) {
        String email = customerRequestDto.getEmail();
        String password = customerRequestDto.getPassword();
        LoginResponse response = new LoginResponse();
        if (customerService.loginCustomer(email,password) != null){
            response.setSuccess(true);
            response.setMessage("Successfully logged in");
            response.setUserEmail(email);
        }
        return response;
    }

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
