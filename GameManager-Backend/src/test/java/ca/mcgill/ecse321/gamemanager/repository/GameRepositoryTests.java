package ca.mcgill.ecse321.gamemanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamemanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamemanager.model.Category;


@SpringBootTest
public class GameRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadGameVal() {
        // create and save category
        Category acategory = new Category("FPS", "First Person Shooting Game");
        acategory = categoryRepo.save(acategory);

        // create game
        String title = "Valorant";
        String description = "An fps game made by Riot";
        double price = 19.99;
        Game.GameStatus GameStatus = Game.GameStatus.InStock;
        String genre = "Tactical shooter";
        int stock = 3;

        Game game_val = new Game(title, description, genre, price, stock, acategory, GameStatus);

        // save game
        game_val = gameRepo.save(game_val);

        // retrieve game
        Game game_valFromDb = gameRepo.findByGameId(game_val.getGameId());

        // assertions
        assertNotNull(game_valFromDb);
        assertEquals(title, game_valFromDb.getTitle());
        // Similar to:
        // if (title != game_valFromDb.getTitle) throw new AssertionFailedError();
        assertEquals(game_val.getGameId(), game_valFromDb.getGameId());
        assertEquals(description, game_valFromDb.getDescription());
        assertEquals(price, game_valFromDb.getPrice());

        assertEquals(acategory.getName(), game_valFromDb.getCategory().getName());

        assertEquals(GameStatus, game_valFromDb.getGameStatus());
        //assertEquals(aRequest, game_valFromDb.getRequest(0));
        //assertTrue(game_valFromDb instanceof GameCopy, "The game should have copy.");
        //assertEquals(game_val.getGameId(), ((GameCopy) game_valFromDb).getGameId());
    }
}