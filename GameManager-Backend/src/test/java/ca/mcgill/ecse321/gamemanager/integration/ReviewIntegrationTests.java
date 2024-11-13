package ca.mcgill.ecse321.gamemanager.integration;


import ca.mcgill.ecse321.gamemanager.dto.ErrorDto;
import ca.mcgill.ecse321.gamemanager.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import ca.mcgill.ecse321.gamemanager.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Define the order to conduct the tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Tell the springBoot to store everything I modified?
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    @AfterAll
    public void cleanUp() {
        gameRepository.deleteAll();
        customerRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    private final int VALID_RATING = 5;
    private final int VALID_NEW_RATING = 1;
    private final int INVALID_RATING = -1;
    private final String VALID_DESCRIPTION = "Excellent game";
    private final String VALID_NEW_DESCRIPTION = "Awful game";
    private static final String INVALID_LONG_DESCRIPTION = "This is a comment designed to be slightly longer than 1000 characters. It includes some filler text to reach the necessary length and demonstrate how one might handle comments of this size within the constraints of the Steam platform. When you write comments on a game, it's easy to get carried away describing the features, graphics, storyline, and gameplay mechanics. For instance, let's talk about the combat system—it's fluid and dynamic, allowing for seamless transitions between moves, and the AI opponents are challenging without feeling unfair. Additionally, the graphics are stunning, with high-resolution textures and beautiful landscapes that make exploration enjoyable. The developers have done a fantastic job in creating an immersive world that keeps you engaged for hours. As you progress, you can unlock new abilities and upgrades, which add depth and replay value. Not to mention, the storyline is compelling and filled with unexpected twists and turns, making you eager to see what happens next. Overall, this game is a masterclass in design and storytelling, and I highly recommend it. I'm excited to see how the game evolves with future updates, as the potential for even more content is enormous. Let's keep supporting such dedicated developers!\n";
    private final String INVALID_DESCRIPTION = "";
    private final String VALID_EMAIL = "example@example.com";
    private int VALID_GAME_ID;
    private final String VALID_GAME_TITLE = "Elden Ring";
    private int savedId;

    @Test
    @Order(1)
    public void testAndCreateValidReview() {

        // set up: add Customer and Game into the repo
        Game game = new Game();
        game.setTitle(VALID_GAME_TITLE);
        gameRepository.save(game);
        VALID_GAME_ID = game.getGameId();
        Customer customer = new Customer("12345","Joe",VALID_EMAIL);
        customerRepository.save(customer);
        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, VALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);


        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/reviews", request, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ReviewResponseDto createdReview = response.getBody();
        savedId = createdReview.getReviewId();
        assertEquals(VALID_RATING, createdReview.getRating());
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_EMAIL, createdReview.getReviewerEmail());
        assertEquals(VALID_GAME_TITLE, createdReview.getGameTitle());
        assertEquals(LocalDate.now(),createdReview.getCreationDate());

    }

    @Test
    @Order(2)
    public void testAndUpdateValidReview() {
        ReviewRequestDto request = new ReviewRequestDto(VALID_NEW_RATING, VALID_NEW_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        //String url = client.getForEntity("/review", String.class).getBody();
        String url = "/reviews/" + savedId;
        client.put(url, request);

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url,ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        ReviewResponseDto createdReview = response.getBody();
        assertEquals(VALID_NEW_RATING, createdReview.getRating());
        assertEquals(VALID_NEW_DESCRIPTION, createdReview.getDescription());

    }

    @Test
    @Order(3)
    public void testAndCreateReviewByInvalidRating() {

        Game game = new Game();
        game.setTitle(VALID_GAME_TITLE);
        gameRepository.save(game);
        VALID_GAME_ID = game.getGameId();
        Customer customer = new Customer("12345","Joe",VALID_EMAIL);
        customerRepository.save(customer);
        ReviewRequestDto request = new ReviewRequestDto(INVALID_RATING, VALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);

        ResponseEntity<String> response = client.postForEntity("/reviews", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    @Order(4)
    public void testAndCreateReviewByLongDescription() {

        Game game = new Game();
        game.setTitle(VALID_GAME_TITLE);
        gameRepository.save(game);
        VALID_GAME_ID = game.getGameId();
        Customer customer = new Customer("12345","Joe",VALID_EMAIL);
        customerRepository.save(customer);
        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, INVALID_LONG_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);

        ResponseEntity<String> response = client.postForEntity("/reviews", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    @Order(5)
    public void testAndCreateReviewByEmptyDescription() {

        Game game = new Game();
        game.setTitle(VALID_GAME_TITLE);
        gameRepository.save(game);
        VALID_GAME_ID = game.getGameId();
        Customer customer = new Customer("12345","Joe",VALID_EMAIL);
        customerRepository.save(customer);
        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, INVALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);

        ResponseEntity<String> response = client.postForEntity("/reviews", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals("Review description is empty", response.getBody());

    }

    @Test
    @Order(6)
    public void testAndUpdateReviewByInvalidId() {
        int invalidReviewId = VALID_GAME_ID+1;
        ReviewRequestDto request = new ReviewRequestDto(VALID_NEW_RATING, VALID_NEW_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        String url = "/reviews/" + invalidReviewId;

        client.put(url, request);
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url,ReviewResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testAndUpdateReviewByLongDescription() {
        ReviewRequestDto request = new ReviewRequestDto(VALID_NEW_RATING, INVALID_LONG_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);

        String url = "/reviews/" + savedId;
        client.put(url, request);

        ResponseEntity<String> response = client.getForEntity(url,String.class);


        assertNotNull(response);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals("Review description out of range", response.getBody());
    }

    @Test
    @Order(8)
    public void testAndUpdateReviewByEmptyDescription() {
        ReviewRequestDto request = new ReviewRequestDto(VALID_NEW_RATING, INVALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        //String url = client.getForEntity("/review", String.class).getBody();
        String url = "/reviews/" + savedId;
        client.put(url, request);

        ResponseEntity<ErrorDto> response = client.getForEntity(url,ErrorDto.class);

        assertNotNull(response);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals("Review description is empty",response.getBody());

    }

    @Test
    @Order(9)
    public void testAndUpdateReviewByInvalidRating() {
        ReviewRequestDto request = new ReviewRequestDto(INVALID_RATING, VALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        //String url = client.getForEntity("/review", String.class).getBody();
        String url = "/reviews/" + savedId;
        client.put(url, request);

        ResponseEntity<String> response = client.getForEntity(url,String.class);

        assertNotNull(response);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals("Review rating out of range", response.getBody());

    }

    @Test
    @Order(10)
    public void testAndDeleteReviewByInvalidId() {
        int invalidReviewId = VALID_GAME_ID+1;
        ReviewRequestDto request = new ReviewRequestDto(VALID_NEW_RATING, VALID_NEW_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        String url = "/reviews/" + invalidReviewId;

        client.delete(url, request);
        ResponseEntity<String> response = client.getForEntity(url,String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    @Order(11)
    public void testAndDeleteValidReview() {
        String url = "/reviews/" + savedId;
        client.delete(url);

        ResponseEntity<String> response = client.getForEntity(url,String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
