package ca.mcgill.ecse321.gamemanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


        Category acategory = new Category("FPS", "First Person Shooting Game");
        acategory = categoryRepo.save(acategory);

        String title = "Valorant";
        String description = "An fps game made by Riot";
        double price = 19.99;
        Game.GameStatus GameStatus = Game.GameStatus.InStock;
        /*Request aRequest = new Request(  Request.RequestType.Addition, Request.RequestStatus.Approved, 1234, Ang );*/
        Game game_val = new Game(title, description, price, acategory, GameStatus);
        game_val = gameRepo.save(game_val);
        Game game_valFromDb = gameRepo.findByGameId(game_val.getGameId());


        assertNotNull(game_valFromDb);
        assertEquals(title, game_valFromDb.getTitle());
        // Similar to:
        // if (title != game_valFromDb.getTitle) throw new AssertionFailedError();
        assertEquals(description, game_valFromDb.getDescription());
        assertEquals(price, game_valFromDb.getPrice());

        assertEquals(acategory.getName(), game_valFromDb.getCategory().getName());
        assertEquals(acategory.getDescription(), game_valFromDb.getCategory().getDescription());

        assertEquals(GameStatus, game_valFromDb.getGameStatus());
        /*assertEquals(aRequest, game_valFromDb.getRequest(0));*/
        assertTrue(game_valFromDb instanceof GameCopy, "The game should have copy.");
        //assertEquals(game_val.getGameId(), ((GameCopy) game_valFromDb).getGameId());


    }
}