package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameCopyRepository extends CrudRepository <GameCopy, Integer> {
    public GameCopy findGameCopyByGameCopyId(int gameCopyId);
    public List<GameCopy> findGameCopiesByGame(Game game);
    public long countByGame(Game game);
}