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

        @Transactional
        public Game updateGame(int id, String aTitle, String aDescription, String aGenre, double aPrice, int aStock, Game.GameStatus aGameStatus, Game.RequestStatus aRequestStatus, Category aCategory) {
            Game game = gameRepo.findByGameId(id);
            if (game ==null){
                throw new IllegalArgumentException("There is no game with ID " + id + ".");
            }
            //Date now = Date.valueOf(LocalDate.now());
            game.setTitle(aTitle);
            game.setDescription(aDescription);
            game.setGenre(aGenre);
            game.setPrice(aPrice);
            game.setStock(aStock);
            game.setGameStatus(aGameStatus);
            game.setRequestStatus(aRequestStatus);
            game.setCategory(aCategory);
            return gameRepo.save(game);
        }
        @Transactional
        public void deleteGame(int id) {
            Game game = gameRepo.findByGameId(id);
            if (game ==null){
                throw new IllegalArgumentException("There is no game with ID " + id + ".");
            }
            gameRepo.delete(game);
        }

        public Iterable<Game> findAllGames() {
            return gameRepo.findAll();
        }



}
