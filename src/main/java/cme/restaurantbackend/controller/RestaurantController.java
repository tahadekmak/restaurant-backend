package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.service.CategoryService;
import cme.restaurantbackend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
    }


    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantsById(@PathVariable(value = "id") Long restaurantID)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(restaurantService.getRestaurantById(restaurantID));
    }

    @GetMapping("/restaurantsByName/{restaurant_name}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByName(@PathVariable(value = "restaurant_name") String restaurantName) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantByName(restaurantName));
    }

    @GetMapping("/restaurantsByCategoryID/{category_id}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCategoryId(@PathVariable(value = "category_id") long categoryID) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantByCategoryId(categoryID));
    }

    @PostMapping("/restaurant/category/{category_id}")
    public ResponseEntity<Restaurant> createRestaurant(@PathVariable(value = "category_id") long categoryId, @Valid @RequestBody Restaurant restaurant) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(restaurantService.createRestaurant(restaurant, categoryId));
    }

    @PutMapping("/restaurant/{id}/category/{category_id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable(value = "id") long restaurantId,
                                                       @PathVariable (value = "category_id") Long categoryId,
                                                       @Valid @RequestBody Restaurant restaurant) throws ResourceNotFoundException {
        restaurant.setId(restaurantId);
        return ResponseEntity.ok().body(restaurantService.updateRestaurant(restaurant, categoryId));
    }

    @DeleteMapping("/restaurant/{id}")
    public HttpStatus deleteRestaurant(@PathVariable(value = "id") Long restaurantId) throws ResourceNotFoundException {
           restaurantService.deleteRestaurant(restaurantId);
        return HttpStatus.OK;
    }

    @PostMapping("/initRestaurant")
    public HttpStatus initRestaurant() {
        restaurantService.initRestaurant();
        return HttpStatus.OK;
    }
}