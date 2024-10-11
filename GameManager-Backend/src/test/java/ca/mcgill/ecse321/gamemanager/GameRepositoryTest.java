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
        String title = "Valorant";
        String description = "An fps game made by Riot";
        double price = 19.99;
        int stock = 999;
        Game.GameStatus GameStatus = Game.GameStatus.InStock;
        Category categories = new Category("FPS","FirstPersonShootingGame");
        //Review reviews = new Review(1234,5,"Good game, take my money", LocalDate.now(), new Game(), Customer())



    }




}

