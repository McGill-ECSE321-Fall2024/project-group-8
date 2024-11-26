package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.dto.GameDto;
import ca.mcgill.ecse321.gamemanager.dto.GameRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.service.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return convertToDto(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category createdCategory = categoryService.createCategory(category);
        //return convertToDto(createdCategory);
        CategoryDto createdDto = convertToDto(createdCategory);
        return new ResponseEntity<CategoryDto>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryService.updateCategory(id, category);
        return convertToDto(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping("/direct-add")
    public ResponseEntity<CategoryDto> addCategoryDirectly(@RequestBody CategoryDto categoryDto) {
        Category createdCategory = new Category(categoryDto.getName(), categoryDto.getDescription());
        CategoryDto newDto = convertToDto(createdCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @DeleteMapping("/direct-remove/{id}")
    public ResponseEntity<Void> removeCategoryDirectly(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getName(), category.getDescription(), category.getGames().stream()
                .map(game -> game.getGameId())
                .collect(Collectors.toList()));
    }
}
