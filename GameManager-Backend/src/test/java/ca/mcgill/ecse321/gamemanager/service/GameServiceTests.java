package ca.mcgill.ecse321.gamemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

@SpringBootTest
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateValidGame() {
        // Set up
        String title = "Test Game";
        String description = "A fun game to play.";
        String genre = "Adventure";
        double price = 29.99;
        int stock = 10;
        Game.GameStatus gameStatus = Game.GameStatus.Available;
        Game.RequestStatus requestStatus = Game.RequestStatus.Approved;
        Category category = new Category("Action", "Action-packed games");

        Game game = new Game(title, description, genre, price, stock, gameStatus, requestStatus, category);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        // Act
        Game createdGame = gameService.createGame(title, description, genre, price, stock, gameStatus, requestStatus, category);

        // Assert
        assertNotNull(createdGame);
        assertEquals(title, createdGame.getTitle());
        assertEquals(description, createdGame.getDescription());
        assertEquals(genre, createdGame.getGenre());
        assertEquals(price, createdGame.getPrice());
        assertEquals(stock, createdGame.getStock());
        assertEquals(gameStatus, createdGame.getGameStatus());
        assertEquals(requestStatus, createdGame.getRequestStatus());
        assertEquals(category, createdGame.getCategory());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void testFindGameById_ValidId() {
        // Set up
        int gameId = 1;
        Game game = new Game("Test Game", "Description", "Genre", 20.0, 5, Game.GameStatus.Available, Game.RequestStatus.Approved, new Category("Action", "Action-packed games"));
        when(gameRepository.findByGameId(gameId)).thenReturn(game);

        // Act
        Game foundGame = gameService.findByGameId(gameId);

        // Assert
        assertNotNull(foundGame);
        assertEquals(game.getTitle(), foundGame.getTitle());
        assertEquals(game.getDescription(), foundGame.getDescription());
        verify(gameRepository, times(1)).findByGameId(gameId);
    }

    @Test
    public void testFindGameById_InvalidId() {
        // Set up
        int gameId = 1;
        when(gameRepository.findByGameId(gameId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> gameService.findByGameId(gameId));
        assertEquals("There is no game with ID " + gameId + ".", exception.getMessage());
    }

    @Test
    public void testSearchGamesByKeyword() {
        // Set up
        String keyword = "Action";
        Game game1 = new Game("Action Game 1", "Description", "Action", 20.0, 5, Game.GameStatus.Available, Game.RequestStatus.Approved, new Category("Action", "Action-packed games"));
        Game game2 = new Game("Action Game 2", "Another description", "Action", 30.0, 2, Game.GameStatus.Available, Game.RequestStatus.Approved, new Category("Action", "Action-packed games"));
        List<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);
        when(gameRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)).thenReturn(games);

        // Act
        List<Game> foundGames = gameService.searchGames(keyword, null, "popularity");

        // Assert
        assertNotNull(foundGames);
        assertEquals(2, foundGames.size());
    }

    @Test
    public void testSearchGamesInvalidKeywordAndCategory() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> gameService.searchGames(null, null, "popularity"));
        assertEquals("Please enter a keyword or select a category.", exception.getMessage());
    }

    @Test
    public void testGetGameDetails_ValidId() {
        // Set up
        int gameId = 1;
        Game game = new Game("Test Game", "Description", "Genre", 20.0, 5, Game.GameStatus.Available, Game.RequestStatus.Approved, new Category("Action", "Action-packed games"));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        GameDto gameDetails = gameService.getGameDetails(gameId);

        // Assert
        assertNotNull(gameDetails);
        assertEquals(game.getTitle(), gameDetails.getTitle());
        assertEquals(game.getDescription(), gameDetails.getDescription());
    }

    @Test
    public void testGetGameDetails_InvalidId() {
        // Set up
        int gameId = 1;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> gameService.getGameDetails(gameId));
        assertEquals("Game not found", exception.getMessage());
    }

    @Test
    public void testHandleInvalidSearch() {
        // Act
        String result = gameService.handleInvalidSearch("");

        // Assert
        assertEquals("Please enter a valid keyword or select a popular category.", result);
    }
}
