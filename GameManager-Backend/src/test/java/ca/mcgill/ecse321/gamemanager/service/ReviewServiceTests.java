package ca.mcgill.ecse321.gamemanager.service;


import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Review;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import ca.mcgill.ecse321.gamemanager.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository mockReviewRepository;

    @Mock
    private GameRepository mockGameRepository;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @InjectMocks
    private ReviewService reviewService;

    private static final int VALID_RATING = 5;
    private static final int INVALID_LOW_RATING = -1;
    private static final int INVALID_HIGH_RATING = 6;

    private static final String INVALID_EMPTY_COMMENT  = "";
    private static final String VALID_COMMENT = "The game is excellent";
    private static final String INVALID_LONG_COMMENT = "This is a test comment designed to be exactly 1001 characters long. It serves as an example for limiting text length in a Java program or other application that requires constraints on user input. Comments like this can become lengthy when users have a lot to share about their experiences, especially on platforms like Steam, where players discuss gameplay mechanics, graphics, and story depth. Let's consider, for example, a game with a complex narrative and intricate gameplay. The developers crafted an open world with rich lore, and the graphics engine renders textures beautifully. This draws players in, giving them an immersive experience with dynamic lighting, weather effects, and character animations that feel lifelike. The game offers a range of missions and side quests, keeping players engaged for hours. Additionally, character customization and skill upgrades add depth. The story has twists, turns, and unexpected outcomes. I would definitely recommend this to fans of the genre!!!";

    // TODO: update with valid and invalid, 4 find methods with valid and invalid cases
    @Test
    public void testAndCreateValidReview() {
        int gameId = 1;
        String customerEmail = "example@example.com";
        Customer customer = new Customer();
        Game game = new Game();
        customer.setEmail(customerEmail);
        game.setGameId(gameId);
        Date date = Date.valueOf(LocalDate.now());


        Review review = new Review(VALID_RATING, VALID_COMMENT,date, customer, game );
        when(mockReviewRepository.save(any(Review.class))).thenReturn(review);
        when(mockGameRepository.findByGameId(any(int.class))).thenReturn(game);
        when(mockCustomerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);


        Review createdReview = reviewService.createReview(VALID_RATING,VALID_COMMENT,customerEmail,gameId);

        assertNotNull(createdReview);
        assertEquals(VALID_RATING, createdReview.getRating());
        assertEquals(VALID_COMMENT,createdReview.getDescription());
        assertEquals(gameId, createdReview.getGame().getGameId());
        assertEquals(customerEmail,createdReview.getCreated().getEmail());

        verify(mockReviewRepository, times(1)).save(createdReview);
    }
    @Test
    public void testAndCreateInvalidReviewWithEmptyComment() {
        int gameId = 1;
        String customerEmail = "example@example.com";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(VALID_RATING, INVALID_EMPTY_COMMENT, customerEmail, gameId));
        //  IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.findPersonById(invalidId));

        assertEquals("review description is null or empty", e.getMessage());

    }

    @Test
    public void testAndCreateInvalidReviewWithLongComment() {
        int gameId = 1;
        String customerEmail = "example@example.com";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(VALID_RATING, INVALID_LONG_COMMENT, customerEmail, gameId));
        assertEquals("Review description out of range", e.getMessage());
    }

    @Test
    public void testAndCreateValidReviewWithLowRating() {
        int gameId = 1;
        String customerEmail = "example@example.com";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(INVALID_LOW_RATING, VALID_COMMENT, customerEmail, gameId));
        assertEquals("Review description out of range", e.getMessage());
    }

    @Test
    public void testAndCreateValidReviewWithHighRating() {
        int gameId = 1;
        String customerEmail = "example@example.com";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(INVALID_HIGH_RATING, VALID_COMMENT, customerEmail, gameId));
        assertEquals("Review description out of range", e.getMessage());
    }

    @Test
    public void testGetReviewByInvalidReviewId() {
        int reviewId = 11;
        when(mockReviewRepository.findReviewByReviewId(reviewId)).thenReturn(null);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reviewService.findReviewById(reviewId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("There is no review with id 11.", ex.getMessage());

    }
    @Test
    public void testGetReviewByValidReviewId() {
        int reviewId = 11;
        int gameId = 1;
        String customerEmail = "example@example.com";
        Customer customer = new Customer();
        Game game = new Game();
        customer.setEmail(customerEmail);
        game.setGameId(gameId);
        Date date = Date.valueOf(LocalDate.now());


        Review review = new Review(VALID_RATING, VALID_COMMENT,date, customer, game );
        when(mockReviewRepository.findReviewByReviewId(reviewId)).thenReturn(review);


        Review gotReview = reviewService.findReviewById(reviewId);

        assertNotNull(gotReview);
        assertEquals(VALID_RATING, gotReview.getRating());
        assertEquals(VALID_COMMENT,gotReview.getDescription());
        assertEquals(gameId, gotReview.getGame().getGameId());
        assertEquals(customerEmail,gotReview.getCreated().getEmail());

        verify(mockReviewRepository, times(1)).findReviewByReviewId(reviewId);

    }

    @Test
    public void testDeleteReviewByValidReviewId(){
        int reviewId = 11;
        int gameId = 1;
        String customerEmail = "example@example.com";
        Customer customer = new Customer();
        Game game = new Game();
        customer.setEmail(customerEmail);
        game.setGameId(gameId);
        Date date = Date.valueOf(LocalDate.now());
        Review review = new Review(VALID_RATING, VALID_COMMENT,date, customer, game );
        when(mockReviewRepository.findReviewByReviewId(any(int.class))).thenReturn(review);
        //when(mockReviewRepository.findById()).thenReturn(review);
        when(mockReviewRepository.delete(review)).thenReturn(true);

        reviewService.deleteReview(reviewId);
        verify(mockReviewRepository, times(1)).delete(review);
    }

    @Test
    public void testDeleteReviewByInvalidCustomerId() {
        int reviewId = 11;
        when(mockReviewRepository.findReviewByReviewId(any(int.class))).thenReturn(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> reviewService.deleteReview(reviewId));

        assertEquals("Review not found", ex.getMessage());
    }

}
