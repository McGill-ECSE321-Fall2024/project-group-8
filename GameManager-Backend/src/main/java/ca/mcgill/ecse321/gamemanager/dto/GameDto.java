package ca.mcgill.ecse321.gamemanager.dto;

import java.util.List;
import java.util.stream.Collectors;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game.GameStatus;
import ca.mcgill.ecse321.gamemanager.model.Game.RequestStatus;

public class GameDto {
    private int gameId;
    private String title;
    private String description;
    private String genre;
    private double price;
    private int stock;
    private int popularity;
    private double averageRating;
    private List<ReviewDto> reviews;
    private GameStatus gameStatus;
    private RequestStatus requestStatus;
    private Category category;

    // Constructor with all attributes
    public GameDto(int gameId, String title, String description, String genre, double price, int stock, int popularity, double averageRating, List<ReviewDto> reviews, GameStatus gameStatus, RequestStatus requestStatus, Category category) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
        this.popularity = popularity;
        this.averageRating = averageRating;
        this.reviews = reviews;
        this.gameStatus = gameStatus;
        this.requestStatus = requestStatus;
        this.category = category;
    }

    // Constructor from Game entity
    public GameDto(Game game) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.genre = game.getGenre();
        this.price = game.getPrice();
        this.stock = game.getStock();
        this.popularity = game.getPopularity();
        this.averageRating = game.getAverageRating();
        this.reviews = game.getReviews().stream().map(ReviewDto::new).collect(Collectors.toList());
        this.gameStatus = game.getGameStatus();
        this.requestStatus = game.getRequestStatus();
        this.category = game.getCategory();
    }

    public GameDto(int gameId, String title, String description, String genre, double price, int stock, int popularity, double averageRating, List<ReviewDto> reviews) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
        this.popularity = popularity;
        this.averageRating = averageRating;
        this.reviews = reviews;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }    

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "gameId=" + gameId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", popularity=" + popularity +
                ", averageRating=" + averageRating +
                ", reviews=" + reviews +
                ", gameStatus=" + gameStatus +
                ", requestStatus=" + requestStatus +
                ", category=" + category +
                '}';
    }
}
