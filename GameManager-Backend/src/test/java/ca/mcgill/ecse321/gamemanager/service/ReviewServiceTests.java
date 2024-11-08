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

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private static final int INVALID_RATING = -1;

    private static final String INVALID_EMPTY_COMMENT  = "";
    private static final String VALID_COMMENT = "The game is excellent";
    private static final String INVALID_LONG_COMMENT = "This is a test comment designed to be exactly 1001 characters long. It serves as an example for limiting text length in a Java program or other application that requires constraints on user input. Comments like this can become lengthy when users have a lot to share about their experiences, especially on platforms like Steam, where players discuss gameplay mechanics, graphics, and story depth. Let's consider, for example, a game with a complex narrative and intricate gameplay. The developers crafted an open world with rich lore, and the graphics engine renders textures beautifully. This draws players in, giving them an immersive experience with dynamic lighting, weather effects, and character animations that feel lifelike. The game offers a range of missions and side quests, keeping players engaged for hours. Additionally, character customization and skill upgrades add depth. The story has twists, turns, and unexpected outcomes. I would definitely recommend this to fans of the genre!!!";

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
    public void testAndCreateInvalidReview() {
        int gameId = 1;
        String customerEmail = "example@example.com";
        Customer customer = new Customer();
        Game game = new Game();
        customer.setEmail(customerEmail);
        game.setGameId(gameId);
        Date date = Date.valueOf(LocalDate.now());

        Review review = new Review(VALID_RATING, INVALID_EMPTY_COMMENT,date, customer, game );


    }


}
