package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Review;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        gameRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testCreateAndLoadReview() {
        // Initialize Category
//        Category category = new Category();
//        category.setName("Test Category");
//        category.setDescription("A category for testing");
//        category = categoryRepository.save(category);
        Category category = new Category("FPS","First Person Shooting Game");

        // Initialize Game
        String tiltle = "Test Game";
        String description = "A great game";
        double price = 59.99;
        Game.GameStatus status = Game.GameStatus.InStock;
        Game game = new Game(tiltle, description, price, category, status);

        // Add a GameCopy to initialize stock (derived from gameCopies list)
        GameCopy gameCopy = new GameCopy(1);
        game.addGameCopy(gameCopy);
        game = gameRepository.save(game);

        // Initialize Customer
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setPassword("password123");
        customer.setEmail("test@customer.com");

        customer = customerRepository.save(customer);

        // Initialize Review
        Review review = new Review();
        review.setRating(5);
        review.setDescription("Excellent game!");
        review.setDate(Date.valueOf("2024-01-01"));
        review.setGame(game);
        review.setReviewer(customer);

        // Save the Review
        review = reviewRepository.save(review);

        // Fetch Review from Database
        Review reviewFromDb = reviewRepository.findById(review.getReviewId()).orElse(null);

        assertNotNull(reviewFromDb);
        assertEquals(5, reviewFromDb.getRating());
        assertEquals("Excellent game!", reviewFromDb.getDescription());
        assertEquals("2024-01-01", reviewFromDb.getDate().toString());
        assertEquals(game.getGameId(), reviewFromDb.getGame().getGameId());
        assertEquals(customer.getEmail(), reviewFromDb.getReviewer().getEmail());
    }
}
