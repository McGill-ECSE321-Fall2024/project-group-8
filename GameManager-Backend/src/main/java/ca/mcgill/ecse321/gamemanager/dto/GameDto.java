package ca.mcgill.ecse321.gamemanager.dto;
import java.time.LocalDate;

import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
public class GameDto {
    private int gameId;
    private String title;
    private String description;
    private String genre;
    private double price;
    private int stock;
    private Game.GameStatus gameStatus;
    private Game.RequestStatus requestStatus;
    private Category category;
    private LocalDate creationDate;

    @SuppressWarnings("unused")
    private GameDto() {
    }

    public GameDto(Game model) {
        this.gameId=model.getGameId();
        this.title=model.getTitle();
        this.description=model.getDescription();
        this.genre=model.getGenre();
        this.price=model.getPrice();
        this.stock=model.getStock();
        this.gameStatus=model.getGameStatus();
        this.requestStatus=model.getRequestStatus();
        this.category=model.getCategory();
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public int getGameId() {
        return gameId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getGenre() {
        return genre;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public Game.GameStatus getGameStatus() {
        return gameStatus;
    }
    public Game.RequestStatus getRequestStatus() {
        return requestStatus;
    }
    public Category getCategory() {
        return category;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setGameStatus(Game.GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
    public void setRequestStatus(Game.RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
