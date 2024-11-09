package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Owner;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import ca.mcgill.ecse321.gamemanager.repository.OwnerRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private GameRepository gameRepo;

    // Retrieve a owner by email (used as ID in this case)
    public Owner findOwnerByEmail(String email) {
        Optional<Owner> optionalOwner = Optional.ofNullable(ownerRepo.findOwnerByEmail(email));
        return optionalOwner.orElseThrow(() -> new IllegalArgumentException("Invalid Owner email."));
    }

    // Retrieve all owners
    public List<Owner> getAllOwners() {
        return (List<Owner>) ownerRepo.findAll();
    }

    // Create a new owner
    @Transactional
    public Owner createOwner(String name, String email, String password) {
        if (ownerRepo.findOwnerByEmail(email) != null) {
            throw new IllegalArgumentException("A owner with this email already exists.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        Owner newOwner = new Owner(name, email, password);
        return ownerRepo.save(newOwner);
    }

    // Update an existing owner by email
    @Transactional
    public Owner updateOwner(String email, String name, String newEmail, String password) {
        Owner owner = findOwnerByEmail(email);
        
        if (name != null && !name.isBlank()) {
            owner.setName(name);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            if (!newEmail.equals(email) && ownerRepo.findOwnerByEmail(newEmail) != null) {
                throw new IllegalArgumentException("New email is already in use by another owner.");
            }
            owner.setEmail(newEmail);
        }
        if (password != null && password.length() >= 8) {
            owner.setPassword(password);
        }

        return ownerRepo.save(owner);
    }

    // Delete a owner by email
    @Transactional
    public void deleteOwner(String email) {
        if (ownerRepo.findOwnerByEmail(email) == null) {
            throw new IllegalArgumentException("Owner with email " + email + " does not exist.");
        }
        ownerRepo.deleteById(email);
    }

    @Transactional
    public Game updateGameDiscount(float discount, int gameId){
        if(discount > 1 || discount < 0) {
            throw new IllegalArgumentException("Discount must be between 0 and 1.");
        }
        Game game = gameRepo.findByGameId(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game with id " + gameId + " does not exist.");
        }
        double currentPrice = game.getPrice();
        currentPrice = discount * currentPrice;
        game.setPrice(currentPrice);
        return gameRepo.save(game);

    }
}
