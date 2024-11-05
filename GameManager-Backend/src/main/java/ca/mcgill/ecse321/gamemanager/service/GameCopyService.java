package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.repository.GameCopyRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameCopyService {
    @Autowired
    private GameCopyRepository gameCopyRepo;
    @Autowired
    private GameRepository gameRepo;

    public GameCopy findGameCopyByGameCopyId(int gameCopyId){
        Optional<GameCopy> optionalGameCopy = Optional.ofNullable(gameCopyRepo.findGameCopyByGameCopyId(gameCopyId));
        return optionalGameCopy.orElseThrow(() -> new IllegalArgumentException("Invalid GameCopy ID."));
    }

    public List<GameCopy> findGameCopiesByGame(int gameId){
        Game game = gameRepo.findByGameId(gameId);
        if (game==null) throw new IllegalArgumentException("Invalid Game ID.");
        return gameCopyRepo.findGameCopiesByGame(game);
    }

    public long countGameCopyOfGame(int gameId) {
        Game game = gameRepo.findByGameId(gameId);
        if (game==null) throw new IllegalArgumentException("Invalid Game ID.");
        return gameCopyRepo.countByGame(game);
    }
    @Transactional
    public GameCopy createGameCopy(int gameId){
        Optional<Game> optionalGame = Optional.ofNullable(gameRepo.findByGameId(gameId));
        Game game = optionalGame.orElseThrow(() -> new IllegalArgumentException("Invalid Game ID."));
        int stock = game.getStock();
        boolean b = game.setStock(stock-1);
        if (b) {
            GameCopy newGameCopy = new GameCopy(game);
            gameRepo.save(game);
            return gameCopyRepo.save(newGameCopy);
        } else {
            String gameTitle = game.getTitle();
            throw new IllegalStateException(gameTitle + " with Game ID " + gameId + " is currently out of stock.");
        }
    }

    @Transactional
    public boolean deleteGameCopy(int gameCopyId){
        boolean wasDelete;
        GameCopy gameCopy = findGameCopyByGameCopyId(gameCopyId);
        int gameId = gameCopy.getGame().getGameId();
        Game game = gameRepo.findByGameId(gameId);
        if (game==null) {
            throw new RuntimeException("Game not found");
        }
        int stock = game.getStock();
        wasDelete = game.setStock(stock+1);
        gameCopyRepo.delete(gameCopy);
        return wasDelete;
    }

}
