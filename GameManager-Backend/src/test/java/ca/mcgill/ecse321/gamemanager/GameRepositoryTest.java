package ca.mcgill.ecse321.gamemanager.repository.;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Request;


@SpringBootTest
public class GameRepositoryTest {
    @Autowired
    private GameRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {repo.deleteAll();}

    @Test





}

