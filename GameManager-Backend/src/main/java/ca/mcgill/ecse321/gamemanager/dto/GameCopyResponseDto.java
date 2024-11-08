package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;

public class GameCopyResponseDto {
    private int gameCopyId;
    private int gameId;
    private String title;
    private String description;
    private String genre;
    private double price;
    private int stock;

    @SuppressWarnings("unused")
    private GameCopyResponseDto(){}

    public GameCopyResponseDto(GameCopy gameCopy) {
        this.gameCopyId = gameCopy.getGameCopyId();
        Game game = gameCopy.getGame();
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.genre = game.getGenre();
        this.price = game.getPrice();
        this.stock = game.getStock();
    }

    public int getGameCopyId() {
        return gameCopyId;
    }
    public int getGameId(){
        return gameId;
    }
    public String getTitle(){
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
}
