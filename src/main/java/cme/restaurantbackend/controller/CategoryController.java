package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.CategoryAbstraction;
import cme.restaurantbackend.repository.CategoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryID)
            throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/category")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryID,
                                                   @Valid @RequestBody Category categoryDetails) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));

        category.setName(categoryDetails.getName());
        final Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/category/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryID)
            throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));

        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/initCategory")
    public void initCategory() {

        try {

            File file = this.getFileFromResource("CATEGORIES_DATA.json");

            final ObjectMapper objectMapper = new ObjectMapper();
            List<CategoryAbstraction> resList = objectMapper.readValue(
                    file,
                    new TypeReference<>() {
                    });

            resList.forEach(x -> {
                System.out.println(x.toString());
                Category category = new Category();
                category.setName(x.getName());

                categoryRepository.save(category);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }
}