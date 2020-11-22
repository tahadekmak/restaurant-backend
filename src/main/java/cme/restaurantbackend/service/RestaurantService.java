package cme.restaurantbackend.service;

import cme.restaurantbackend.MyFunctions;
import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.repository.CategoryRepository;
import cme.restaurantbackend.repository.RestaurantRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }


    public Restaurant getRestaurantById(long restaurantId) throws ResourceNotFoundException {

        Optional<Restaurant> restaurantDb = restaurantRepository.findById(restaurantId);

        if (restaurantDb.isPresent()) {
            return restaurantDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + restaurantId);
        }
    }

    public List<Restaurant> getRestaurantByName(String restaurantName) {

        return restaurantRepository.findByName(restaurantName.toLowerCase());
    }

    public List<Restaurant> getRestaurantByCategoryId(long categoryId) {

        return restaurantRepository.findByCategoryId(categoryId);
    }

    public Restaurant createRestaurant(Restaurant restaurant, long categoryId) throws ResourceNotFoundException {
        Optional <Category> categoryDb = categoryRepository.findById(categoryId);
        if(categoryDb.isPresent()) {
            restaurant.setCategory(categoryDb.get());
            return restaurantRepository.save(restaurant);
        }
        else {
            throw new ResourceNotFoundException("Record not found with id : " + categoryId);
        }
    }

    public Restaurant updateRestaurant(Restaurant restaurant, long categoryId) throws ResourceNotFoundException {
        Optional <Restaurant> restaurantDb = restaurantRepository.findById(restaurant.getId());

        if (restaurantDb.isPresent()) {

            Optional <Category> categoryDb = categoryRepository.findById(categoryId);
            if(categoryDb.isPresent()) {

                Restaurant restaurantUpdate = restaurantDb.get();
                restaurantUpdate.setId(restaurant.getId());
                restaurantUpdate.setName(restaurant.getName());
                restaurantUpdate.setAverageCost(restaurant.getAverageCost());
                restaurantUpdate.setAddress(restaurant.getAddress());
                restaurantUpdate.setPhoneNumber(restaurant.getPhoneNumber());
                restaurantUpdate.setImage(restaurant.getImage());
                restaurantUpdate.setCategory(categoryDb.get());
                restaurantRepository.save(restaurantUpdate);
                return restaurantUpdate;
            }

            else {
                throw new ResourceNotFoundException("Record not found with id : " + categoryId);
            }

        } else {
            throw new ResourceNotFoundException("Record not found with id : " + restaurant.getId());
        }
    }

    public void deleteRestaurant(long restaurantId) throws ResourceNotFoundException {
        Optional <Restaurant> restaurantDb = restaurantRepository.findById(restaurantId);

        if (restaurantDb.isPresent()) {
            restaurantRepository.delete(restaurantDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + restaurantId);
        }
    }

    public void initRestaurant() {

            try {

                File file = MyFunctions.getFileFromResource("RESTAURANTS_DATA.json");

                final ObjectMapper objectMapper = new ObjectMapper();
                List<Restaurant> resList = objectMapper.readValue(
                        file,
                        new TypeReference<>() {
                        });

                resList.forEach(x -> {

                    Restaurant restaurant = new Restaurant();

                    Long categoryID = x.getCategory().getId();
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
                        restaurant.setImage(MyFunctions.imageToByteArray(Arrays.toString(x.getImage())));
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
    }