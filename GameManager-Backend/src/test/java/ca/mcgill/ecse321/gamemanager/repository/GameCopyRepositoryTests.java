package ca.mcgill.ecse321.gamemanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ca.mcgill.ecse321.gamemanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class GameCopyRepositoryTests {
    @Autowired
    private GameCopyRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadGameValCopy() {

        GameCopy game_val_copy = new GameCopy();
        game_val_copy = repo.save(game_val_copy);
        GameCopy game_val_copyFromDb = repo.findGameCopyByGameCopyId(game_val_copy.getGameCopyId());


        assertNotNull(game_val_copyFromDb);
        assertEquals(game_val_copy.getGameCopyId(), game_val_copyFromDb.getGameCopyId());

    }
}