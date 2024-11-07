package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.dto.ReviewDto;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Get a game by ID.
     */
    @GetMapping("/{id}")
    public GameDto findByGameId(@PathVariable int id) {
        Game game = gameService.findByGameId(id);
        return new GameDto(game);
    }

    /**
     * Create a new game.
     */
    @PostMapping
    public GameDto createGame(@RequestBody GameDto gameDto) {
        Game createdGame = gameService.createGame(
                gameDto.getTitle(),
                gameDto.getDescription(),
                gameDto.getGenre(),
                gameDto.getPrice(),
                gameDto.getStock(),
                gameDto.getGameStatus(),
                gameDto.getRequestStatus(),
                gameDto.getCategory()
        );
        return new GameDto(createdGame);
    }

    /**
     * Search games by keyword or category with sorting options.
     */
    @GetMapping("/search")
    public List<GameDto> searchGames(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "popularity") String sortBy
    ) {
        List<Game> games = gameService.searchGames(keyword, category, sortBy);
        return games.stream().map(GameDto::new).collect(Collectors.toList());
    }

    /**
     * Get detailed game information, including reviews.
     */
    @GetMapping("/{id}/details")
    public GameDto getGameDetails(@PathVariable int id) {
        return gameService.getGameDetails(id);
    }

    /**
     * Handle invalid search input with suggestions.
     */
    @GetMapping("/search/handleInvalid")
    public String handleInvalidSearch(@RequestParam String keyword) {
        return gameService.handleInvalidSearch(keyword);
    }
}
