package ca.mcgill.ecse321.gamemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamemanager.dto.OwnerDto;
import ca.mcgill.ecse321.gamemanager.dto.OwnerRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Owner;
import ca.mcgill.ecse321.gamemanager.service.OwnerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/{email}")
    public OwnerDto findOwnerByEmail(@PathVariable String email) {
        Owner owner = ownerService.findOwnerByEmail(email);
        return convertToDto(owner);
    }

    @GetMapping
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public OwnerDto createOwner(@RequestBody OwnerRequestDto ownerRequestDto) {
        Owner createdOwner = ownerService.createOwner(
                ownerRequestDto.getName(),
                ownerRequestDto.getEmail(),
                ownerRequestDto.getPassword());
        return convertToDto(createdOwner);
    }

    @PutMapping("/{email}")
    public OwnerDto updateOwner(@PathVariable String email, @RequestBody OwnerRequestDto ownerRequestDto) {
        Owner updatedOwner = ownerService.updateOwner(
                email,
                ownerRequestDto.getName(),
                ownerRequestDto.getEmail(),
                ownerRequestDto.getPassword());
        return convertToDto(updatedOwner);
    }

    @DeleteMapping("/{email}")
    public void deleteOwner(@PathVariable String email) {
        ownerService.deleteOwner(email);
    }

    private OwnerDto convertToDto(Owner owner) {
        return new OwnerDto(owner.getName(), owner.getEmail());
    }
}
