package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Transactional
    public Customer createCustomer(String name, String email, String password) {

        if (customerRepo.findCustomerByEmail(email) != null) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        Customer newCustomer = new Customer(password,name, email);
        return customerRepo.save(newCustomer);
    }



    // Retrieve a customer by email (used as ID in this case)
    public Customer findCustomerByEmail(String email) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if(customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }

        return customer;
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepo.findAll();
    }

    // Create a new customer


    // Update an existing customer by email
    @Transactional
    public Customer updateCustomer(String email, String name, String newEmail, String password) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }
        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid name.");

        }
        if (newEmail != null && !newEmail.isBlank()) {
            if (!email.equals(newEmail) && customerRepo.findCustomerByEmail(newEmail) != null) {
                throw new IllegalArgumentException("New email is already in use by another customer.");
            }
            customer.setEmail(newEmail);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid email.");

        }
        if (password != null && password.length() >= 8) {
            customer.setPassword(password);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid password.");
        }

        return customerRepo.save(customer);
    }

    // Delete a customer by email
    @Transactional
    public void deleteCustomer(String email) {

        Customer customer = findCustomerByEmail(email);

        if (customer == null) {
            throw new IllegalArgumentException("Customer with email " + email + " does not exist.");
        }
        customerRepo.delete(customer);
    }

    @Transactional
    public PurchaseOrder getPurchaseOrder(String email, int orderId) {

        Customer customer = customerRepo.findCustomerByEmail(email);
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findByOrderId(orderId);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }

        return purchaseOrder;

    }

    @Transactional
    public List<PurchaseOrder> getPurchaseOrders(String email)
    {
        Customer customer = customerRepo.findCustomerByEmail(email);
        List<PurchaseOrder> PurchaseOrders = customer.getPurchaseOrders();
        return PurchaseOrders;
    }

    @Transactional
    public int numberOfPurchaseOrders (String email)
    {
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getPurchaseOrders().size();
    }

    @Transactional
    public boolean hasPurchaseOrders(String email)
    {
        Customer customer = customerRepo.findCustomerByEmail(email);
        return !customer.getPurchaseOrders().isEmpty();
    }

    @Transactional
    public int indexOfPurchaseOrder(String email, int orderId){
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findByOrderId(orderId);
        Customer customer = customerRepo.findCustomerByEmail(email);
        int index = customer.getPurchaseOrders().indexOf(purchaseOrder);
        return index;
    }

    @Transactional
    public Game getInWishlist(String email, int index)
    {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game=customer.getInWishlist(index);
        return game;
    }

    @Transactional
    public List<Game> getInWishlist(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        List<Game> games = customer.getInWishlist();
        return games;

    }
    @Transactional
    public  int numberOfInWishlist(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getInWishlist().size();
    }

    @Transactional
    public boolean hasInWishlist(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return !customer.getInWishlist().isEmpty();

    }

    @Transactional
    public Game getInCart(String email, int index){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getInCart().get(index);
    }

    @Transactional
    public List<Game> getInCart(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getInCart();
    }

    @Transactional
    public int numberOfInCart(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getInCart().size();
    }

    @Transactional
    public int hasInCart(String email){
        Customer customer = customerRepo.findCustomerByEmail(email);
        return customer.getInCart().size();
    }

    @Transactional
    public Customer addPurchaseOrder(String email,int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findByOrderId(orderId);
        List<PurchaseOrder> purchaseOrders = customer.getPurchaseOrders();
        purchaseOrders.add(purchaseOrder);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer removePurchaseOrder(String email, int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findByOrderId(orderId);
        List<PurchaseOrder> purchaseOrders = customer.getPurchaseOrders();
        purchaseOrders.remove(purchaseOrder);
        return customerRepo.save(customer);
    }

    @Transactional
    public Customer addInWishlist(String email, int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(orderId);
        List<Game> games = customer.getInWishlist();
        games.add(game);
        return customerRepo.save(customer);
    }

    @Transactional
    public Customer removeInWishlist(String email, int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(orderId);
        List<Game> games = customer.getInWishlist();
        games.remove(game);
        return customerRepo.save(customer);
    }

    @Transactional
    public Customer addInCart(String email, int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(orderId);
        List<Game> cart = customer.getInCart();
        cart.add(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer removeInCart(String email, int orderId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(orderId);
        List<Game> cart = customer.getInCart();
        cart.remove(game);
        return customerRepo.save(customer);
    }


}
