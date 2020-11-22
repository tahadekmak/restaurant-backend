package cme.restaurantbackend.Tests;

import cme.restaurantbackend.RestaurantBackendApplication;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllRestaurants() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/restaurant",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetRestaurantsByName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/restaurantsByName/KFC",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetRestaurantsByCategoryID() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/restaurantsByCategoryID/1",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetRestaurantById() {
        Restaurant restaurant = restTemplate.getForObject(getRootUrl() + "/restaurant/1", Restaurant.class);
        System.out.println(restaurant.getName());
        assertNotNull(restaurant);
    }

    @Test
    public void testCreateRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("KFC");
        restaurant.setAverageCost(15);
        restaurant.setAddress("Beirut");

        Category category = new Category();
        category.setId(1L);
        category.setName("American");

        ResponseEntity<Restaurant> postResponse = restTemplate.postForEntity(getRootUrl() + "/restaurant/category/" + category.getId(), restaurant, Restaurant.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateRestaurant() {
        int id = 1;
        Restaurant restaurant = restTemplate.getForObject(getRootUrl() + "/restaurant/" + id, Restaurant.class);
        restaurant.setName("KFC");
        restaurant.setAverageCost(20);
        restaurant.setAddress("Beirut");

        Category category = new Category();
        category.setId(1L);
        category.setName("American");

        restTemplate.put(getRootUrl() + "/person/" + id, restaurant);
        Restaurant updatedRestaurant = restTemplate.getForObject(getRootUrl() + "/restaurant/" + id + "/category/" + category.getId(), Restaurant.class);
        assertNotNull(updatedRestaurant);
    }

    @Test
    public void testDeleteRestaurant() {
        int id = 1;
        Restaurant restaurant = restTemplate.getForObject(getRootUrl() + "/restaurant/" + id, Restaurant.class);
        assertNotNull(restaurant);
        restTemplate.delete(getRootUrl() + "/restaurant/" + id);
        try {
            restTemplate.getForObject(getRootUrl() + "/restaurant/" + id, Restaurant.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
