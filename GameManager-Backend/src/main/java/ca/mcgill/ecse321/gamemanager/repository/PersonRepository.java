package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

    public Person findByEmail(String Email);
}
