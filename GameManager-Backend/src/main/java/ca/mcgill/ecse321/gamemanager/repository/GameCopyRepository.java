package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import org.springframework.data.repository.CrudRepository;

public interface GameCopyRepository extends CrudRepository <GameCopy, Integer> {
    public GameCopy findGameCopyByGameCopyId(int gameCopyId);
}