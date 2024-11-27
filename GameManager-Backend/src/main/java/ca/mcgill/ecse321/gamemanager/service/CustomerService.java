package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.exception.GameManagerException;
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

import java.util.List;

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
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "A customer with this email already exists.");
            //throw new IllegalArgumentException("A customer with this email already exists.");
        }
        if (password == null || password.length() < 8) {
           // throw new IllegalArgumentException("Password must be at least 8 characters long.");
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        String encryptedPassword = SHA256Encryption.getSHA(password);

        Customer newCustomer = new Customer(encryptedPassword,name, email);
        return customerRepo.save(newCustomer);
    }

    @Transactional
    public Customer loginCustomer(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email or password cannot be empty.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        String encryptedPassword = SHA256Encryption.getSHA(password);
        if(!customer.getPassword().equals(encryptedPassword)) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Customer with this password does not match.");
        }
        return customer;
    }
    @Transactional
    public Customer addInCart(int gameId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        Game game = gameRepo.findByGameId(gameId);
        if (game == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Game with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.addInCart(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer removeInCart(int gameId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        Game game = gameRepo.findByGameId(gameId);
        if (game == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Game with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.removeInCart(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public List<Game> getAllInCart(String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        return customer.getInCart();
    }


    @Transactional
    public Customer addInWishlist(int gameId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        Game game = gameRepo.findByGameId(gameId);
        if (game == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Game with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.addInWishlist(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer removeInWishList(int gameId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        Game game = gameRepo.findByGameId(gameId);
        if (game == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Game with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.removeInWishlist(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer addPurchaseOrder(int orderId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        PurchaseOrder order = purchaseOrderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Order with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.addPurchaseOrder(order);
        return customerRepo.save(customer);

    }
    @Transactional
    public Customer removePurchaseOrder(int orderId, String email){
        if (email == null || email.isEmpty()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST, "Email cannot be empty.");
        }
        PurchaseOrder order = purchaseOrderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Order with this id does not exist.");
        }
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND, "Customer with this email does not exist.");
        }
        customer.removePurchaseOrder(order);
        return customerRepo.save(customer);

    }




    // Retrieve a customer by email (used as ID in this case)
    public Customer findCustomerByEmail(String email) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if(customer == null) {
            //throw new GameManagerException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));

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
    public Customer updateCustomer(String email, String newName, String newPassword) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }
        if (newName != null && !newName.isBlank()) {
            customer.setName(newName);
        }
        else{
            throw new GameManagerException(HttpStatus.BAD_REQUEST,"Failed to update customer with invalid name.");

        }
        if (email.isBlank()) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST,"Failed to update customer with invalid email.");
        }

        if (newPassword != null && newPassword.length() >= 8) {
            customer.setPassword(newPassword);
        }
        else{
            throw new GameManagerException(HttpStatus.BAD_REQUEST,"Failed to update customer with invalid password.");
        }

        return customerRepo.save(customer);
    }

    // Delete a customer by email
    @Transactional
    public void deleteCustomer(String email) {


        Customer customer = customerRepo.findCustomerByEmail(email);

        if (customer == null) {
            throw new GameManagerException(HttpStatus.BAD_REQUEST,"Customer with email " + email + " does not exist.");
        }
        customerRepo.delete(customer);
    }

    @Transactional
    public PurchaseOrder getPurchaseOrder(String email, int orderId) {

        Customer customer = customerRepo.findCustomerByEmail(email);
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findByOrderId(orderId);
        if (customer == null) {
            throw new GameManagerException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
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
    public Customer addInWishlist(String email, int gameId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(gameId);
        List<Game> games = customer.getInWishlist();
        games.add(game);
        return customerRepo.save(customer);
    }

    @Transactional
    public Customer removeInWishlist(String email, int gameId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(gameId);
        List<Game> games = customer.getInWishlist();
        games.remove(game);
        return customerRepo.save(customer);
    }

    @Transactional
    public Customer addInCart(String email, int gameId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(gameId);
        List<Game> cart = customer.getInCart();
        cart.add(game);
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer removeInCart(String email, int gameId) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        Game game= gameRepo.findByGameId(gameId);
        List<Game> cart = customer.getInCart();
        cart.remove(game);
        return customerRepo.save(customer);
    }


}
