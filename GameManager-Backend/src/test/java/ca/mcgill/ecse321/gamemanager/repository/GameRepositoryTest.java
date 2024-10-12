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
public class GameRepositoryTest {
    @Autowired
    private GameRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {repo.deleteAll();}

    @Test
    public void testCreateAndReadGameVal()
    {
        int GameId = 12345 ;
        String title = "Valorant";
        String description = "An fps game made by Riot";
        double price = 19.99;
        Category acategory = new Category("FPS","First Person Shooting Game");
        Game.GameStatus GameStatus = Game.GameStatus.InStock;
        Request aRequest = new Request( 12345, Request.RequestType.Addition, Request.RequestStatus.Approved, 1234, new Employee("12345","Ang" ,"n@gmail.com"));
        Game game_val = new Game( GameId, title,description,  price, acategory,
        GameStatus, aRequest);
        game_val = repo.save(game_val);
        Game game_valFromDb = repo.findGameByGameId(game_val.getGameId());


        assertNotNull(game_valFromDb);
        assertEquals(title, game_valFromDb.getTitle());
        // Similar to:
        // if (title != game_valFromDb.getTitle) throw new AssertionFailedError();
        assertEquals(description, game_valFromDb.getDescription());
        assertEquals(price, game_valFromDb.getPrice());
        assertEquals(acategory, game_valFromDb.getCategory());
        assertEquals(GameStatus, game_valFromDb.getGameStatus());
        /*assertEquals(aRequest, game_valFromDb.getRequest(0));*/
        assertTrue(game_valFromDb instanceof GameCopy, "The game should have copy.");
        assertEquals(GameId, ((GameCopy) game_valFromDb).getGameId());


    }




}

