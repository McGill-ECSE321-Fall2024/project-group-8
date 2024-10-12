package ca.mcgill.ecse321.gamemanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.gamemanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamemanager.model.Category;

import java.time.LocalDate;


@SpringBootTest
public class GameRepositoryTest {
    @Autowired
    private GameRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {repo.deleteAll();}

    @Test
    public void testCreateAndReadGame()
    {
        int GameId = 12345 ;
        String title = "Valorant";
        String description = "An fps game made by Riot";
        double price = 19.99;
        Category acategory = new Category("FPS","First Person Shooting Game");
        Game.GameStatus GameStatus = Game.GameStatus.InStock;
        Request aRequest = new Request( 12345, Request.RequestType.Addition, Request.RequestStatus.Approved, 1234, new Employee("12345","Ang" ,"n@gmail.com"));
        Game game_val = new Game( GameId, title,description,  price, acategory,
        GameStatus);
        game_val = repo.save(game_val);
        Game game_valFromDb = repo.findByGameId(game_val.getGameId());



    }




}
