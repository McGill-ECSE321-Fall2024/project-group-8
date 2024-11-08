package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.dto.ReviewDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Autowired
    private GameRepository gameRepository;

    /**
     * Retrieve a game by ID, throwing an exception if not found.
     *
     * @param id The ID of the game.
     * @return The game with the specified ID.
     */
    public Game findByGameId(int id) {
        Game game = gameRepository.findByGameId(id);
        if (game == null) {
            throw new IllegalArgumentException("There is no game with ID " + id + ".");
        }
        return game;
    }

    /**
     * Create a new game with the provided attributes and save it in the repository.
     *
     * @param aTitle        The title of the game.
     * @param aDescription  The description of the game.
     * @param aGenre        The genre of the game.
     * @param aPrice        The price of the game.
     * @param aStock        The stock of the game.
     * @param aGameStatus   The status of the game.
     * @param aRequestStatus The request status of the game.
     * @param aCategory     The category of the game.
     * @return The created game.
     */
    @Transactional
    public Game createGame(String aTitle, String aDescription, String aGenre, double aPrice, int aStock, Game.GameStatus aGameStatus, Game.RequestStatus aRequestStatus, Category aCategory) {
        Game gameToCreate = new Game(aTitle, aDescription, aGenre, aPrice, aStock, aGameStatus, aRequestStatus, aCategory);
        return gameRepository.save(gameToCreate);
    }

    /**
     * Search games by keyword or category and sort by popularity, rating, and relevance.
     *
     * @param keyword The search keyword (optional).
     * @param category The category to filter by (optional).
     * @param sortBy The sorting criteria, e.g., "popularity", "rating", "releaseDate".
     * @return A list of games matching the search criteria.
     */
    @Transactional
    public List<Game> searchGames(String keyword, String category, String sortBy) {
        List<Game> games;

        // If no keyword and no category is provided, return popular categories or a message
        if ((keyword == null || keyword.isBlank()) && (category == null || category.isBlank())) {
            throw new IllegalArgumentException("Please enter a keyword or select a category.");
        }

        // Search games by keyword and category
        if (keyword != null && !keyword.isBlank()) {
            games = gameRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        } else {
            games = gameRepository.findByCategoryName(category);
        }


        /*@Transactional
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
        }*/




        // Sort games based on the selected sorting criteria
        games = sortGames(games, sortBy);

        // Return games if found, otherwise throw an exception
        if (games.isEmpty()) {
            throw new IllegalArgumentException("Game not found");
        }
        return games;
    }

    /**
     * Helper method to sort games based on different criteria.
     *
     * @param games  The list of games to sort.
     * @param sortBy The sorting criteria.
     * @return A sorted list of games.
     */
    private List<Game> sortGames(List<Game> games, String sortBy) {
        if ("popularity".equalsIgnoreCase(sortBy)) {
            return games.stream().sorted(Comparator.comparingInt(Game::getPopularity).reversed()).collect(Collectors.toList());
        } else if ("rating".equalsIgnoreCase(sortBy)) {
            return games.stream().sorted(Comparator.comparingDouble(Game::getAverageRating).reversed()).collect(Collectors.toList());
        }
        return games;  // Default, unsorted
    }

    /**
     * Retrieve game details, including description, images, and reviews.
     *
     * @param gameId The ID of the game.
     * @return The game with detailed information.
     */
    @Transactional
    public GameDto getGameDetails(int gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game game = optionalGame.orElseThrow(() -> new IllegalArgumentException("Game not found"));

        // Convert Game entity to GameDto
        return convertToDto(game);
    }

    // Helper method to convert Game entity to GameDto
    private GameDto convertToDto(Game game) {
        List<ReviewDto> reviewDtos = game.getReviews().stream()
            .map(review -> new ReviewDto(review.getReviewId(), review.getDescription(), review.getRating()))
            .collect(Collectors.toList());

        return new GameDto(
            game.getGameId(),
            game.getTitle(),
            game.getDescription(),
            game.getGenre(),
            game.getPrice(),
            game.getStock(),
            game.getPopularity(),
            game.getAverageRating(),
            reviewDtos
        );
    }

    /**
     * Handle an invalid search input or no search results found.
     *
     * @param keyword The invalid search keyword entered by the user.
     * @return Suggested keywords or popular categories to help the customer.
     */
    public String handleInvalidSearch(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return "Please enter a valid keyword or select a popular category.";
        }
        return "No results found for \"" + keyword + "\". Please try again or check the spelling.";
    }

}
