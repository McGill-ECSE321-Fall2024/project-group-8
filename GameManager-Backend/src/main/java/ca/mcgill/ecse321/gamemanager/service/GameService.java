package ca.mcgill.ecse321.gamemanager.service;
import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class GameService {
        @Autowired
        private static GameRepository gameRepo;
        public static Game findByGameId(int id){
            Game game= gameRepo.findByGameId(id);
            if (game ==null){
                throw new IllegalArgumentException("There is no game with ID " + id + ".");
            }
            return game;

        }
        @Transactional
        public Game createGame(String aTitle, String aDescription, String aGenre, double aPrice, int aStock, Game.GameStatus aGameStatus, Game.RequestStatus aRequestStatus, Category aCategory) {
            Date now = Date.valueOf(LocalDate.now());
            Game gameToCreate = new Game(aTitle, aDescription, aGenre, aPrice, aStock, aGameStatus,  aRequestStatus, aCategory);
            return gameRepo.save(gameToCreate);
        }

}
