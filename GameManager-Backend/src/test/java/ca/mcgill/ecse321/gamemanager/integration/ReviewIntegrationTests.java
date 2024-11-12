package ca.mcgill.ecse321.gamemanager.integration;


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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private final String VALID_DESCRIPTION = "Excellent game";
    private final String VALID_EMAIL = "example@example.com";
    private final int VALID_GAME_ID = 3;

    @Test
    @Order(1)
    public void testAndCreateValidReview() {

        // set up: add Customer and Game into the repo
        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, VALID_DESCRIPTION, VALID_EMAIL, VALID_GAME_ID);
        Game game = new Game();
        // TODO: game and customer instance need more specific attributes
        game.setGameId(VALID_GAME_ID);
        gameRepository.save(game);
        Customer customer = new Customer("12345","Joe",VALID_EMAIL);
        customerRepository.save(customer);

        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/review", request, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ReviewResponseDto createdReview = response.getBody();




    }

    @Test
    @Order(2)
    public void testAndUpdateValidReview() {

    }

    @Test
    @Order(3)
    public void testAndDeleteValidReview() {

    }

    @Test
    @Order(4)
    public void testAndCreateInvalidReview() {

    }

    @Test
    @Order(5)
    public void testAndUpdateInvalidReview() {

    }

    @Test
    @Order(6)
    public void testAndDeleteInvalidReview() {

    }



}
