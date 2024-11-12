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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;

import java.util.List;

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
    private final int INVALID_GAME_COPY_ID = -1;
    private final int INVALID_GAME_ID = -1;

    private int VALID_GAME_COPY_ID;
    private int VALID_GAME_ID;


    @Test
    @Order(1)
    @Commit
    public void testCreateValidGameCopy() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
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

    @Test
    @Order(3)
    public void testFindGameCopyByValidGameCopyId() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);
        ResponseEntity<GameCopyResponseDto> postResponse = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);
        VALID_GAME_COPY_ID = postResponse.getBody().getGameCopyId();
        VALID_GAME_ID = postResponse.getBody().getGameDto().getGameId();
        String url = "/gamecopy/" + this.VALID_GAME_COPY_ID;

        // Act
        ResponseEntity<GameCopyResponseDto> response = client.getForEntity(url, GameCopyResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameCopyResponseDto gameCopyResponseDto = response.getBody();
        assertNotNull(gameCopyResponseDto);
        assertEquals(VALID_GAME_COPY_ID, gameCopyResponseDto.getGameCopyId());
        assertEquals(VALID_GAME_ID, gameCopyResponseDto.getGameDto().getGameId());
        assertEquals(STOCK-1, gameCopyResponseDto.getGameDto().getStock());
        assertEquals(PRICE, gameCopyResponseDto.getGameDto().getPrice());
    }



    @Test
    @Order(4)
    public void testFindGameCopyByInvalidGameCopyId(){
        // Arrange
        String url = "/gamecopy/" + this.INVALID_GAME_COPY_ID;

        // Act
        ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Invalid GameCopy ID.", body.getErrors().get(0));
    }

    @Test
    @Order(5)
    public void testFindGameCopyByValidGameId() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        Game savedGame = gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);
        ResponseEntity<GameCopyResponseDto> postResponse = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);
        VALID_GAME_COPY_ID = postResponse.getBody().getGameCopyId();
        VALID_GAME_ID = postResponse.getBody().getGameDto().getGameId();
        String url = "/game/"+ savedGame.getGameId() + "/gamecopies";

        // Act
        ResponseEntity<List<GameCopyResponseDto>> response = client.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GameCopyResponseDto>>() {}
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<GameCopyResponseDto> gameCopyResponseDtos = response.getBody();
        GameCopyResponseDto gameCopyResponseDto = gameCopyResponseDtos.get(0);
        assertNotNull(gameCopyResponseDtos);
        assertTrue(gameCopyResponseDtos.size()>0, "The number of GameCopy found should be positive.");
        assertEquals(VALID_GAME_COPY_ID, gameCopyResponseDto.getGameCopyId());
        assertEquals(VALID_GAME_ID, gameCopyResponseDto.getGameDto().getGameId());
        assertEquals(STOCK-1, gameCopyResponseDto.getGameDto().getStock());
        assertEquals(PRICE, gameCopyResponseDto.getGameDto().getPrice());
    }

    @Test
    @Order(6)
    public void testFindGameCopyByInvalidGameId() {
        // Arrange
        String url = "/game/" + INVALID_GAME_ID + "/gamecopies";

        // Act
        ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Invalid Game ID.", body.getErrors().get(0));
    }

    @Test
    @Order(7)
    public void testCountGameCopyOfGame() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);
        ResponseEntity<GameCopyResponseDto> postResponse = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);
        VALID_GAME_COPY_ID = postResponse.getBody().getGameCopyId();
        VALID_GAME_ID = postResponse.getBody().getGameDto().getGameId();
        String url = "/game/" + VALID_GAME_ID + "/gamecopies/count";

        // Act
        ResponseEntity<Integer> response = client.getForEntity(url, Integer.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());

    }

    @Test
    @Order(8)
    public void testCountGameCopyOfGameWithoutGameCopy() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        Game savedGame = gameRepository.save(game);
        VALID_GAME_ID = savedGame.getGameId();
        String url = "/game/" + VALID_GAME_ID + "/gamecopies/count";

        // Act
        ResponseEntity<Integer> response = client.getForEntity(url, Integer.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody());
    }

    @Test
    @Order(8)
    public void testDeleteValidGameCopy() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);
        ResponseEntity<GameCopyResponseDto> postResponse = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);
        VALID_GAME_COPY_ID = postResponse.getBody().getGameCopyId();
        String url = "/gamecopy/" + VALID_GAME_COPY_ID;

        // Act
        client.delete(url, GameCopyResponseDto.class);
        ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid GameCopy ID.", response.getBody().getErrors().get(0));
    }

    @Test
    @Order(9)
    public void testDeleteInvalidGameCopy() {
        // Arrange
        Game game = new Game();
        game.setStock(STOCK);
        game.setPrice(PRICE);
        game.setTitle(TITLE);
        gameRepository.save(game);
        GameDto gameDto = new GameDto(game);
        GameCopyRequestDto request = new GameCopyRequestDto(gameDto);
        ResponseEntity<GameCopyResponseDto> postResponse = client.postForEntity("/gamecopy", request, GameCopyResponseDto.class);
        VALID_GAME_COPY_ID = postResponse.getBody().getGameCopyId();
        VALID_GAME_ID = postResponse.getBody().getGameDto().getGameId();
        String deleteUrl = "/gamecopy/" + INVALID_GAME_COPY_ID;
        String findUrl = "/gamecopy/" + VALID_GAME_COPY_ID;

        // Act
        client.delete(deleteUrl, GameCopyResponseDto.class);
        ResponseEntity<GameCopyResponseDto> response = client.getForEntity(findUrl, GameCopyResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameCopyResponseDto gameCopyResponseDto = response.getBody();
        assertNotNull(gameCopyResponseDto);
        assertEquals(VALID_GAME_COPY_ID, gameCopyResponseDto.getGameCopyId());
        GameDto respondseGameDto = gameCopyResponseDto.getGameDto();
        assertNotNull(respondseGameDto);
        assertEquals(VALID_GAME_ID, respondseGameDto.getGameId());
        assertEquals(STOCK-1, respondseGameDto.getStock());
        assertEquals(TITLE, respondseGameDto.getTitle());
        assertEquals(PRICE, respondseGameDto.getPrice());
    }
}
