package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Category;
import org.junit.jupiter.api.AfterEach;
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
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadCategory() {
        // Initialize test data
        String name = "Test Category";
        String description = "Test Description";

        // Save Category
        Category testCategory = new Category(name, description);
        testCategory = repo.save(testCategory);

        // Retrieve Category by ID
        Category categoryFromDb = repo.findCategoryById(testCategory.getId());  // Updated method name and ID getter

        // Assertions
        assertNotNull(categoryFromDb);
        assertEquals(name, categoryFromDb.getName());
        assertEquals(description, categoryFromDb.getDescription());
    }
}
