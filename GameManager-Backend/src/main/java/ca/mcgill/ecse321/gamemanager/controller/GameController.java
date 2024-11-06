package ca.mcgill.ecse321.gamemanager.controller;
import ca.mcgill.ecse321.gamemanager.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.service.GameService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;





    @PostMapping("/game")
    public GameDto createGame(@RequestBody GameDto game){
        Game createdGame = gameService.createGame(game.getTitle(),game.getDescription(), game.getGenre(), game.getPrice(), game.getGameId(), game.getGameStatus(),game.getRequestStatus(),game.getCategory());

        return new GameDto(createdGame);
    }

    @PutMapping("/game/{id}")
    public GameDto updateGame(@PathVariable int id, @RequestBody GameDto game){
        String title = game.getTitle();
        String description = game.getDescription();
        String genre = game.getGenre();
        double price = game.getPrice();
        int stock = game.getStock();
        Game.GameStatus gameStatus = game.getGameStatus();
        Game.RequestStatus requestStatus = game.getRequestStatus();
        Category category = game.getCategory();
        Game updateGame= gameService.updateGame(id,title,description,genre,price,stock,gameStatus,requestStatus,category);
        return new GameDto(updateGame);

    }



    @GetMapping("/game/{id}")
    public GameDto findByGameId(@PathVariable int id) {
        Game game = GameService.findByGameId(id);
        return new GameDto(game);
    }

    @GetMapping("/game")
    public List<GameDto> getAllGames() {
        Iterable<Game> games = gameService.findAllGames();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            gameDtos.add(new GameDto(game));
        }
        return gameDtos;
    }

    @DeleteMapping("/game/{id}")
    public void deleteGame(@PathVariable int id) {
        gameService.deleteGame(id);
    }

}
