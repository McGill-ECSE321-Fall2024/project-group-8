package ca.mcgill.ecse321.gamemanager.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamemanager.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String> {
    public Owner findOwnerByEmail(String email);
}
