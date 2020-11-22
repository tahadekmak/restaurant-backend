package cme.restaurantbackend.service;

import cme.restaurantbackend.MyFunctions;
import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.repository.CategoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> getAllCategories() {
        return repo.findAll();
    }


    public Category getCategoryById(long categoryId) throws ResourceNotFoundException {

        Optional<Category> categoryDb = repo.findById(categoryId);

        if (categoryDb.isPresent()) {
            return categoryDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + categoryId);
        }
    }

    public Category createCategory(Category category) {
        return repo.save(category);
    }

    public Category updateCategory(Category category) throws ResourceNotFoundException {
        Optional <Category> categoryDb = repo.findById(category.getId());

        if (categoryDb.isPresent()) {
            Category categoryUpdate = categoryDb.get();
            categoryUpdate.setId(category.getId());
            categoryUpdate.setName(category.getName());
            repo.save(categoryUpdate);
            return categoryUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + category.getId());
        }
    }

    public void deleteCategory(long categoryId) throws ResourceNotFoundException {
        Optional <Category> categoryDb = repo.findById(categoryId);

        if (categoryDb.isPresent()) {
            repo.delete(categoryDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + categoryId);
        }
    }

    public void initCategory() {
        try {
            File file = MyFunctions.getFileFromResource("CATEGORIES_DATA.json");
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Category> resList = objectMapper.readValue(
                    file,
                    new TypeReference<>() {
                    });

            resList.forEach(x -> {
                Category category = new Category();
                category.setName(x.getName());

                repo.save(category);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}