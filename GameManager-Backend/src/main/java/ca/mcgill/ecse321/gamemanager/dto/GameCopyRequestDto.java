package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;

public class GameCopyRequestDto {
    private int gameCopyId;
    private GameDto gameDto;
    public GameCopyRequestDto(GameDto aGameDto) {
        this.gameDto = aGameDto;
    }

    public int getGameCopyId() {
        return gameCopyId;
    }
    public GameDto getGame(){
        return this.gameDto;
    }
    public void setGame(GameDto aGameDto) {
        this.gameDto = aGameDto;
    }
}
