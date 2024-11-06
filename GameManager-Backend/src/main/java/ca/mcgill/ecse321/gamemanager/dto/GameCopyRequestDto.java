package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;

public class GameCopyRequestDto {
    private int gameCopyId;
    private Game game;
    public GameCopyRequestDto(Game aGame) {
        this.game = aGame;
    }

    public int getGameCopyId() {
        return gameCopyId;
    }
    public Game getGame(){
        return this.game;
    }
    public void setGame(Game aGame) {
        this.game = aGame;
    }
}
