package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.GameCopyRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.GameCopyResponseDto;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.service.GameCopyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameCopyController {
    @Autowired
    private GameCopyService gameCopyService;

    /**
     * Return the gameCopy with the given ID.
     *
     * @param gcid The primary key of the gameCopy to find.
     * @return The gameCopy with the given ID.
     */
    @GetMapping("/gamecopy/{gcid}")
    public GameCopyResponseDto findGameCopyByGameCopyId(@PathVariable int gcid){
        GameCopy gameCopy = gameCopyService.findGameCopyByGameCopyId(gcid);
        return new GameCopyResponseDto(gameCopy);
    }

    @GetMapping("/gamecopy/{gameid}")
    public List<GameCopyResponseDto> findGameCopiesByGame(@PathVariable int gameid){
        List<GameCopy> gameCopies = gameCopyService.findGameCopiesByGame(gameid);
        List<GameCopyResponseDto> gameCopyResponseDtos = new ArrayList<>();
        for (GameCopy eachGC : gameCopies) {
            gameCopyResponseDtos.add(new GameCopyResponseDto(eachGC));
        }
        return gameCopyResponseDtos;
    }

    @GetMapping("/gamecopy/counts/{gameid}")
    public long countGameCopyOfGame(@PathVariable int gameid) {
        return gameCopyService.countGameCopyOfGame(gameid);
    }

    @PostMapping("/gamecopy")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCopyResponseDto createGameCopy(@RequestBody GameCopyRequestDto gameCopy) {
        GameCopy savedGameCopy = gameCopyService.createGameCopy(gameCopy.getGame().getGameId());
        return new GameCopyResponseDto(savedGameCopy);
    }

    @DeleteMapping("/gamecopy/{gameid}")
    public ResponseEntity<Void> deleteGameCopy(@PathVariable int gameid){
        gameCopyService.returnGameCopy(gameid);
        return ResponseEntity.noContent().build();
    }
}
