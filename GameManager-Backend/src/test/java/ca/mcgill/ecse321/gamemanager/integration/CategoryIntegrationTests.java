package ca.mcgill.ecse321.gamemanager.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CategoryIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CategoryRepository categoryRepository;

    private int validCategoryId;

    @BeforeAll
    public void clearDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidCategory() {
        CategoryDto request = new CategoryDto(0, "Adventure", "Exciting adventure games", null);
        ResponseEntity<CategoryDto> response = client.postForEntity("/categories", request, CategoryDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CategoryDto createdCategory = response.getBody();
        assertNotNull(createdCategory);
        assertEquals("Adventure", createdCategory.getName());
        assertEquals("Exciting adventure games", createdCategory.getDescription());
        assertNotNull(createdCategory.getCategoryId());

        // Save the ID for further tests
        this.validCategoryId = createdCategory.getCategoryId();
    }

    @Test
    @Order(2)
    public void testGetCategoryByValidId() {
        String url = "/categories/" + this.validCategoryId;

        ResponseEntity<CategoryDto> response = client.getForEntity(url, CategoryDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CategoryDto category = response.getBody();
        assertNotNull(category);
        assertEquals("Adventure", category.getName());
        assertEquals("Exciting adventure games", category.getDescription());
        assertEquals(this.validCategoryId, category.getCategoryId());
    }

    @Test
    @Order(3)
    public void testGetCategoryByInvalidId() {
        String url = "/categories/999";

        ResponseEntity<String> response = client.getForEntity(url, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category with ID 999 not found", response.getBody());
    }

    @Test
    @Order(4)
    public void testUpdateCategory() {
        String url = "/categories/" + this.validCategoryId;
        CategoryDto updatedRequest = new CategoryDto(this.validCategoryId, "Adventure Updated", "Updated adventure games", null);

        client.put(url, updatedRequest);

        // Retrieve updated category
        ResponseEntity<CategoryDto> response = client.getForEntity(url, CategoryDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CategoryDto updatedCategory = response.getBody();
        assertNotNull(updatedCategory);
        assertEquals("Adventure Updated", updatedCategory.getName());
        assertEquals("Updated adventure games", updatedCategory.getDescription());
    }

    @Test
    @Order(5)
    public void testDeleteCategory() {
        String url = "/categories/" + this.validCategoryId;
        client.delete(url);

        // Attempt to retrieve deleted category
        ResponseEntity<String> response = client.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

