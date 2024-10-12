package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PersonRepositoryTests {

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void cleanDatabase() {personRepository.deleteAll();}

    @Test
    public void testCreateAndReadPerson(){
        String Password = "personPassword";
        String Username = "personName";
        String email = "PersonEmail@gmail.com";

        Person tPerson = new Person(Password, Username, email);

        personRepository.save(tPerson);
        //tPerson = personRepository.save(tPerson);

        Person personFromDB = personRepository.findByEmail(email);

        assertNotNull(personFromDB);
        assertEquals(Password, personFromDB.getPassword());
        assertEquals(Username, personFromDB.getName());
        assertEquals(email, personFromDB.getEmail());



    }
}
