package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.repository.GameCopyRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameCopyServiceTests {
    @Mock
    private GameCopyRepository gameCopyRepository;
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameCopyService gameCopyService;
    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateValidGameCopy(){
        // set up game
        String title = "Game1";
        String description = "good";
        String genre = "nice";
        double price = 0.1;
        int stock = 1;
        Game.GameStatus gameStatus = Game.GameStatus.Available;
        Game.RequestStatus requestStatus = Game.RequestStatus.Approved;
        Category category = new Category("mystery", "hard");

        Game game = new Game(title, description, genre, price, stock, gameStatus, requestStatus, category);
        gameRepository.save(any(Game.class));
        GameCopy gameCopy = new GameCopy(game);
        gameRepository.save(game);
        when(gameCopyRepository.save(any(GameCopy.class))).thenReturn(gameCopy);

        // act
        Game savedGame = gameRepository.save(game);

        // Retrieve the saved game by ID and assert it's not null
        Optional<Game> retrievedGame = gameRepository.findById(savedGame.getGameId());
        assertTrue(retrievedGame.isPresent());
//        GameCopy createdGameCopy = gameCopyService.createGameCopy(game.getGameId());

        // assert
//        assertNotNull(createdGameCopy);
//        assertNotNull(createdGameCopy.getGame());
//        assertEquals(game.getGameId(), createdGameCopy.getGame().getGameId());
//        verify(gameCopyRepository, times(1)).save(createdGameCopy);
    }

}
