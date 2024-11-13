package ca.mcgill.ecse321.gamemanager.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.repository.CategoryRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;
    
    private Integer validGameId;
    private Integer validCategoryId;

    private final String VALID_TITLE = "Test Game";
    private final String VALID_DESCRIPTION = "An exciting test game.";
    private final String VALID_GENRE = "Adventure";
    private final double VALID_PRICE = 29.99;
    private final int VALID_STOCK = 10;

    @BeforeAll
    public void setup() {
        categoryRepository.deleteAll();
        gameRepository.deleteAll();

        // Create a category for testing
        CategoryDto categoryRequest = new CategoryDto(0, "Adventure", "Adventure games", null);
        ResponseEntity<CategoryDto> categoryResponse = client.postForEntity("/api/categories", categoryRequest, CategoryDto.class);
        assertNotNull(categoryResponse.getBody());
        this.validCategoryId = categoryResponse.getBody().getCategoryId();
    }

    @AfterAll
    public void teardown() {
        categoryRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        GameDto request = new GameDto(0, VALID_TITLE, VALID_DESCRIPTION, VALID_GENRE, VALID_PRICE, VALID_STOCK, 0, 0.0, null);
        request.setCategoryId(validCategoryId);

        // Act
        ResponseEntity<GameDto> response = client.postForEntity("/api/games", request, GameDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GameDto createdGame = response.getBody();
        assertNotNull(createdGame);
        assertEquals(VALID_TITLE, createdGame.getTitle());
        assertEquals(VALID_DESCRIPTION, createdGame.getDescription());
        assertEquals(VALID_GENRE, createdGame.getGenre());
        assertEquals(VALID_PRICE, createdGame.getPrice());
        assertEquals(VALID_STOCK, createdGame.getStock());
        assertNotNull(createdGame.getGameId());

        // Save the ID for further tests
        this.validGameId = createdGame.getGameId();
    }

    @Test
    @Order(2)
    public void testFindGameById() {
        // Arrange
        String url = "/api/games/" + this.validGameId;

        // Act
        ResponseEntity<GameDto> response = client.getForEntity(url, GameDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameDto foundGame = response.getBody();
        assertNotNull(foundGame);
        assertEquals(VALID_TITLE, foundGame.getTitle());
        assertEquals(VALID_DESCRIPTION, foundGame.getDescription());
    }

    @Test
    @Order(3)
    public void testUpdateGame() {
        // Arrange
        String url = "/api/games/" + this.validGameId;
        GameDto updatedRequest = new GameDto(this.validGameId, "Updated Game Title", "Updated description", "RPG", 49.99, 20, 5, 4.5, null);
        updatedRequest.setCategoryId(validCategoryId);

        // Act
        client.put(url, updatedRequest);

        // Retrieve updated game
        ResponseEntity<GameDto> response = client.getForEntity(url, GameDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameDto updatedGame = response.getBody();
        assertNotNull(updatedGame);
        assertEquals("Updated Game Title", updatedGame.getTitle());
        assertEquals("Updated description", updatedGame.getDescription());
        assertEquals("RPG", updatedGame.getGenre());
        assertEquals(49.99, updatedGame.getPrice());
        assertEquals(20, updatedGame.getStock());
    }

    @Test
    @Order(4)
    public void testDeleteGame() {
        // Arrange
        String url = "/api/games/" + this.validGameId;
    
        // Act
        client.delete(url);
    
        // Attempt to retrieve deleted game
        ResponseEntity<String> response = client.getForEntity(url, String.class);
    
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    
        // Debugging: Print the response body
        System.out.println("Response body: " + response.getBody());
    
        // Check if the body is not null and contains the expected error message
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().contains("There is no game with ID"), "Expected error message not found in response body");
    }    

    @Test
    @Order(5)
    public void testFindGameByInvalidId() {
        // Arrange
        String url = "/api/games/999";

        // Act
        ResponseEntity<String> response = client.getForEntity(url, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("There is no game with ID"));
    }
}
