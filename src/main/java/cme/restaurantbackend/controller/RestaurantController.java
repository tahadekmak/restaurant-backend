package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.model.RestaurantAbstraction;
import cme.restaurantbackend.model.RestaurantData;
import cme.restaurantbackend.repository.CategoryRepository;
import cme.restaurantbackend.repository.RestaurantRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/restaurant")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/restaurantsByName/{restaurant_name}")
    public List<RestaurantData> getRestaurantsByName(@PathVariable(value = "restaurant_name") String restaurantName) {
        return restaurantRepository.findByName(restaurantName.toLowerCase());
    }

    @GetMapping("/restaurantsByCategoryID/{category_id}")
    public List<RestaurantData> getRestaurantsByCategoryId(@PathVariable(value = "category_id") Long categoryID) {
        return restaurantRepository.findByCategoryId(categoryID);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantsById(@PathVariable(value = "id") Long restaurantID)
            throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));
        return ResponseEntity.ok().body(restaurant);
    }

    @PostMapping("/restaurant")
    public Restaurant createRestaurant(@Valid @RequestBody RestaurantAbstraction restaurantAbstraction) throws ResourceNotFoundException, IOException {

        Long categoryID = restaurantAbstraction.getCategoryID();
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantAbstraction.getName());
        restaurant.setAverageCost(restaurantAbstraction.getAverageCost());
        restaurant.setAddress(restaurantAbstraction.getAddress());
        restaurant.setPhoneNumber(restaurantAbstraction.getPhoneNumber());
        restaurant.setImage(imageToByteArray(restaurantAbstraction.getImage()));
        restaurant.setCategory(category);
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable(value = "id") Long RestaurantID,
                                                       @Valid @RequestBody RestaurantAbstraction restaurantAbstraction) throws ResourceNotFoundException, IOException {

        Restaurant restaurant = restaurantRepository.findById(RestaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + RestaurantID));

        Long categoryID = restaurantAbstraction.getCategoryID();
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));

        restaurant.setName(restaurantAbstraction.getName());
        restaurant.setAverageCost(restaurantAbstraction.getAverageCost());
        restaurant.setAddress(restaurantAbstraction.getAddress());
        restaurant.setPhoneNumber(restaurantAbstraction.getPhoneNumber());
        restaurant.setImage(imageToByteArray(restaurantAbstraction.getImage()));
        restaurant.setCategory(category);

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

    @PostMapping("/initRestaurant")
    public void initRestaurant() {

        try {

            File file = this.getFileFromResource("RESTAURANTS_DATA.json");

            final ObjectMapper objectMapper = new ObjectMapper();
            List<RestaurantAbstraction> resList = objectMapper.readValue(
                    file,
                    new TypeReference<>() {
                    });

            resList.forEach(x -> {

                Restaurant restaurant = new Restaurant();

                Long categoryID = x.getCategoryID();
                Category category = null;
                try {
                    category = categoryRepository.findById(categoryID)
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryID));
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                }

                restaurant.setName(x.getName());
                restaurant.setAverageCost(x.getAverageCost());
                restaurant.setAddress(x.getAddress());
                restaurant.setPhoneNumber(x.getPhoneNumber());
                try {
                    restaurant.setImage(imageToByteArray(x.getImage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                restaurant.setCategory(category);

                restaurantRepository.save(restaurant);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] imageToByteArray(String path) throws IOException {
        InputStream in = RestaurantController.class.getClassLoader().getResourceAsStream(path);
        assert in != null;
        BufferedImage bImage = ImageIO.read(in);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        return bos.toByteArray();
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