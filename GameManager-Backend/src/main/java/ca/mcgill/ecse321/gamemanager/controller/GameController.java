package ca.mcgill.ecse321.gamemanager.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.service.GameService;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    @GetMapping("/game/{id}")
    public GameDto findByGameId(@PathVariable int id) {
        Game game = gameService.findByGameId(id);
        return new GameDto(game);
    }
    @PostMapping("/game")
    public GameDto createGame(@RequestBody GameDto game){
        Game createdGame = gameService.createGame(game.getTitle(),game.getDescription(), game.getGenre(), game.getPrice(), game.getGameId(), game.getGameStatus(),game.getRequestStatus(),game.getCategory());
        return new GameDto(createdGame);
    }

}
