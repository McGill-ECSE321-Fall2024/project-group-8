package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.repository.CategoryRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;

    // Retrieve all categories
    @Transactional
    public List<CategoryDto> getAllCategories() {
        return ((List<Category>) categoryRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Retrieve a category by ID with associated games
    @Transactional
    public CategoryDto getCategoryWithGames(int id) {
        Category category = categoryRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + id + " not found"));

        List<Integer> gameIds = category.getGames().stream()
                .map(Game::getGameId)
                .collect(Collectors.toList());

        return new CategoryDto(category.getCategoryId(), category.getName(), category.getDescription(), gameIds);
    }

    // Create a new category with validation
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
        }
        if (categoryDto.getDescription() == null || categoryDto.getDescription().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category description cannot be empty");
        }

        Category category = new Category(categoryDto.getName(), categoryDto.getDescription());
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    // Update an existing category by ID
    @Transactional
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + id + " not found"));

        if (categoryDto.getName() != null && !categoryDto.getName().isBlank()) {
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null && !categoryDto.getDescription().isBlank()) {
            category.setDescription(categoryDto.getDescription());
        }

        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    // Delete a category by ID
    @Transactional
    public void deleteCategory(int id) {
        if (!categoryRepository.existsById(String.valueOf(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + id + " not found");
        }
        categoryRepository.deleteById(String.valueOf(id));
    }

    // Helper method to convert Category to CategoryDto
    private CategoryDto convertToDto(Category category) {
        List<Integer> gameIds = category.getGames().stream()
                .map(Game::getGameId)
                .collect(Collectors.toList());
        return new CategoryDto(category.getCategoryId(), category.getName(), category.getDescription(), gameIds);
    }
}
