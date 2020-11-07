package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/restaurant")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantsById(@PathVariable(value = "id") Long restaurantID)
            throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));
        return ResponseEntity.ok().body(restaurant);
    }

    @PostMapping("/restaurant")
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable(value = "id") Long RestaurantID,
                                                       @Valid @RequestBody Restaurant restaurantDetails) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(RestaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + RestaurantID));

        restaurant.setName(restaurantDetails.getName());
        restaurant.setType(restaurantDetails.getType());
        restaurant.setAverageCost(restaurantDetails.getAverageCost());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());

        final Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/restaurant/{id}")
    public Map<String, Boolean> deleteRestaurant(@PathVariable(value = "id") Long RestaurantID)
            throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(RestaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + RestaurantID));

        restaurantRepository.delete(restaurant);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}