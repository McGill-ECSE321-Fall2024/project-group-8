package ca.mcgill.ecse321.gamemanager.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamemanager.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
    public Game findGameByGameId(int id);
}




