package ca.mcgill.ecse321.gamemanager.repository;


import ca.mcgill.ecse321.gamemanager.model.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepo;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        ownerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner() {
        String name = "OwnerName";
        String password = "OwnerPassword";
        String email = "OwnerEmail";

        Owner testOwner = new Owner(password, name, email);

        ownerRepo.save(testOwner);
        Owner ownerFromDB = ownerRepo.findOwnerByEmail(email);

        assertNotNull(ownerFromDB);
        assertEquals(name, ownerFromDB.getName());
        assertEquals(password, ownerFromDB.getPassword());
        assertEquals(email, ownerFromDB.getEmail());
    }
}