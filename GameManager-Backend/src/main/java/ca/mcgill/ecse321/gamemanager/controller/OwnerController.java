package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.*;
import ca.mcgill.ecse321.gamemanager.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.model.Owner;
import ca.mcgill.ecse321.gamemanager.service.OwnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;


    @GetMapping
    public OwnerResponseDto getOwner() {
       Owner owner = ownerService.getOwner();

        return new OwnerResponseDto(owner);
    }

    @GetMapping("/IsOwner/{email}")
    public boolean isOwner(@PathVariable String email) {
        return ownerService.isOwner(email);
    }


    @PostMapping
    public OwnerResponseDto createOwner(@RequestBody OwnerRequestDto ownerRequestDto) {
        Owner createdOwner = ownerService.createOwner(
                ownerRequestDto.getName(),
                ownerRequestDto.getEmail(),
                ownerRequestDto.getPassword());
        return new OwnerResponseDto(createdOwner);
    }

    @PutMapping("/{email}")
    public OwnerResponseDto updateOwner(@PathVariable String email, @RequestBody OwnerRequestDto ownerRequestDto) {


        Owner updatedOwner = ownerService.updateOwner(
                email,
                ownerRequestDto.getName(),
                ownerRequestDto.getPassword());
        return new OwnerResponseDto(updatedOwner);
    }


    @PutMapping("/discount/{gameId}")
    public GameDto updateDiscount(@PathVariable int gameId, @RequestParam float discount) {
        Game game = ownerService.updateGameDiscount(discount, gameId);

        return new GameDto(game);

    }

}
