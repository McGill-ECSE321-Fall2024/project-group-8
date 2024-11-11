package ca.mcgill.ecse321.gamemanager.integration;


import ca.mcgill.ecse321.gamemanager.dto.ErrorDto;
import ca.mcgill.ecse321.gamemanager.dto.GameCopyRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.GameCopyResponseDto;
import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.GameCopyRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameCopyIntegrationTests {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameCopyRepository gameCopyRepository;

    @Autowired
    private TestRestTemplate client;

    private final String TITLE = "MineCraft";
    private final double PRICE = 0.1;
    private final int STOCK = 10;
    private final int INVALID_GAME_ID = -1;

    private int VALID_GAME_COPY_ID;
    private int VALID_GAME_ID;


//    @BeforeEach
//    @AfterEach
//    public void clearDatabase() {
//        gameCopyRepository.deleteAll();
//        gameRepository.deleteAll();
//    }

    @Test
    @Order(1)
    public void testCreateValidGameCopy() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle("MineCraft");
        gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);

        // Act
        ResponseEntity<GameCopyResponseDto> response = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        GameCopyResponseDto createdGameCopyDto = response.getBody();
        assertNotNull(createdGameCopyDto);
        GameDto responsedGameDto = createdGameCopyDto.getGameDto();

        assertEquals(gameDto.getGameId(), responsedGameDto.getGameId());
        assertEquals(TITLE, responsedGameDto.getTitle());
        assertEquals(PRICE, responsedGameDto.getPrice());
        assertEquals(STOCK-1, responsedGameDto.getStock());
        assertTrue(createdGameCopyDto.getGameCopyId() > 0, "GameCopyResponse should have a positive ID.");
        assertTrue(responsedGameDto.getGameId() > 0, "GameResponse should have a positive ID.");
        this.VALID_GAME_COPY_ID = createdGameCopyDto.getGameCopyId();
        System.out.println("first");
        System.out.println(VALID_GAME_COPY_ID);
        this.VALID_GAME_ID = responsedGameDto.getGameId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidGameCopy() {
        // Arrange
        Game game = new Game();
        game.setGameId(INVALID_GAME_ID);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);

        // Act
        ResponseEntity<ErrorDto> response = client.postForEntity("/gamecopy", request, ErrorDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Invalid Game ID.", body.getErrors().get(0));
    }

//    @Test
//    @Order(3)
//    public void testFindGameCopyByValidGameCopyId() {
//        // Arrange
//        System.out.println("third");
//        System.out.println(VALID_GAME_COPY_ID);
//        String url = "/gamecopy/" + this.VALID_GAME_COPY_ID;
//
//        // Act
//        ResponseEntity<GameCopyResponseDto> response = client.getForEntity(url, GameCopyResponseDto.class);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        GameCopyResponseDto gameCopyResponseDto = response.getBody();
//        assertNotNull(gameCopyResponseDto);
//        assertEquals(VALID_GAME_COPY_ID, gameCopyResponseDto.getGameCopyId());
//        assertEquals(VALID_GAME_ID, gameCopyResponseDto.getGameDto().getGameId());
//        assertEquals(STOCK, gameCopyResponseDto.getGameDto().getStock());
//        assertEquals(PRICE, gameCopyResponseDto.getGameDto().getPrice());
//    }

}
