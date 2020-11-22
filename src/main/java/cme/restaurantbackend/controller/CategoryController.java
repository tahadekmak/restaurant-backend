package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity <List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.createCategory(category));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id,
                                               @Valid @RequestBody Category category) throws ResourceNotFoundException {
        category.setId(id);
        return ResponseEntity.ok().body(categoryService.updateCategory(category));
    }

    @DeleteMapping("/category/{id}")
    public HttpStatus deleteCategory(@PathVariable long id) throws ResourceNotFoundException {
        categoryService.deleteCategory(id);
        return HttpStatus.OK;
    }

    @PostMapping("/initCategory")
    public HttpStatus initCategory() {
        categoryService.initCategory();
        return HttpStatus.OK;
    }
}