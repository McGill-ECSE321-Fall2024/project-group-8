package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class GameRequestDto {
    @NotBlank(message = "Game name is required.")
    private String name;
    @NotBlank(message = "Game title is required.")
    private String title;
    @NotBlank(message = "Game description is required.")
    private String description;
    @NotBlank(message = "Game genre is required.")
    private String genre;
    @NotBlank(message = "Game price is required.")
    private double price;
    @NotBlank(message = "Game stock is required.")
    private int stock;
    @NotBlank(message = "Game status is required.")
    private Game.GameStatus gameStatus;
    @NotBlank(message = "Game request is required.")
    private Game.RequestStatus requestStatus;
    @NotBlank(message = "Game category is required.")
    private Category category;


    public GameRequestDto(String name,int gameId, String title, String description, String genre, double price, int stock, Game.GameStatus gameStatus, Game.RequestStatus requestStatus, Category category) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
        this.gameStatus=gameStatus;
        this.requestStatus=requestStatus;
        this.category=new Category();

    }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public Game.GameStatus getGameStatus() { return gameStatus; }
    public Game.RequestStatus getRequestStatus() { return requestStatus; }
    public Category getCategory() { return category; }


}
