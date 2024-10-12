package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){ repo.deleteAll();}

    @Test
    public void testCreateAndReadCategory() {

        String name = "Test Category";
        String description = "Test Description";

        Category jTestCategory = new Category(name, description);

        jTestCategory = repo.save(jTestCategory);
        Category CategoryFromDB = repo.findCategoryByName(jTestCategory.getName());

        assertNotNull(CategoryFromDB);
        assertEquals(name, CategoryFromDB.getName());
        assertEquals(description, CategoryFromDB.getDescription());
    }

}