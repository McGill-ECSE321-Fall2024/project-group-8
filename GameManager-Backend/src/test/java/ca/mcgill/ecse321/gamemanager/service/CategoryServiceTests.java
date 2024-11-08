package ca.mcgill.ecse321.gamemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.repository.CategoryRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateValidCategory() {
        // Arrange
        String name = "Adventure";
        String description = "Exciting adventure games";
        CategoryDto categoryDto = new CategoryDto(0, name, description, Collections.emptyList());
        Category category = new Category(name, description);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);

        // Assert
        assertNotNull(createdCategory);
        assertEquals(name, createdCategory.getName());
        assertEquals(description, createdCategory.getDescription());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        Category category1 = new Category("Adventure", "Exciting adventure games");
        category1.setCategoryId(1);

        Category category2 = new Category("Action", "High-paced action games");
        category2.setCategoryId(2);

        List<Category> categories = Arrays.asList(category1, category2);

        // Mock the findAll method to return the list of categories
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDto> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(categories.stream().map(Category::getName).collect(Collectors.toList()),
                result.stream().map(CategoryDto::getName).collect(Collectors.toList()));
        assertEquals(categories.stream().map(Category::getDescription).collect(Collectors.toList()),
                result.stream().map(CategoryDto::getDescription).collect(Collectors.toList()));
    }

    @Test
    public void testGetCategoryWithGamesByValidId() {
        // Arrange
        int id = 1;
        Category category = new Category("Adventure", "Exciting adventure games");
        category.setCategoryId(id);
        when(categoryRepository.findById(String.valueOf(id))).thenReturn(Optional.of(category));

        // Act
        CategoryDto foundCategory = categoryService.getCategoryWithGames(id);

        // Assert
        assertNotNull(foundCategory);
        assertEquals("Adventure", foundCategory.getName());
        assertEquals("Exciting adventure games", foundCategory.getDescription());
        verify(categoryRepository, times(1)).findById(String.valueOf(id));
    }

    @Test
    public void testGetCategoryWithGamesByInvalidId() {
        // Arrange
        int invalidId = 42;
        when(categoryRepository.findById(String.valueOf(invalidId))).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoryService.getCategoryWithGames(invalidId);
        });
        assertEquals("404 NOT_FOUND \"Category with ID " + invalidId + " not found\"", exception.getMessage());
        verify(categoryRepository, times(1)).findById(String.valueOf(invalidId));
    }

    @Test
    public void testDeleteCategoryByValidId() {
        // Arrange
        int categoryId = 1;
        when(categoryRepository.existsById(String.valueOf(categoryId))).thenReturn(true);

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(String.valueOf(categoryId));
    }

    @Test
    public void testDeleteCategoryByInvalidId() {
        // Arrange
        int invalidId = 42;
        when(categoryRepository.existsById(String.valueOf(invalidId))).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoryService.deleteCategory(invalidId);
        });
        assertEquals("404 NOT_FOUND \"Category with ID " + invalidId + " not found\"", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(String.valueOf(invalidId));
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        int id = 1;
        String oldName = "Adventure";
        String oldDescription = "Exciting adventure games";
        String newName = "Action-Adventure";
        String newDescription = "Thrilling action and adventure games";

        // Original category in the repository
        Category category = new Category(oldName, oldDescription);
        category.setCategoryId(id);

        // Updated information
        CategoryDto updatedCategoryDto = new CategoryDto(id, newName, newDescription, Collections.emptyList());

        when(categoryRepository.findById(String.valueOf(id))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        CategoryDto result = categoryService.updateCategory(id, updatedCategoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(newName, result.getName());
        assertEquals(newDescription, result.getDescription());
        verify(categoryRepository, times(1)).findById(String.valueOf(id));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
