package cme.restaurantbackend.Tests;

import cme.restaurantbackend.RestaurantBackendApplication;
import cme.restaurantbackend.model.Category;
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
public class CategoryTest {
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
    public void testGetAllCategories() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/category",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCategoryById() {
        Category category = restTemplate.getForObject(getRootUrl() + "/category/1", Category.class);
        System.out.println(category.getName());
        assertNotNull(category);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        category.setName("American");
        ResponseEntity<Category> postResponse = restTemplate.postForEntity(getRootUrl() + "/category", category, Category.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateCategory() {
        int id = 1;
        Category category = restTemplate.getForObject(getRootUrl() + "/category/" + id, Category.class);
        category.setName("Turkish");
        restTemplate.put(getRootUrl() + "/category/" + id, category);
        Category updatedCategory = restTemplate.getForObject(getRootUrl() + "/category/" + id, Category.class);
        assertNotNull(updatedCategory);
    }

    @Test
    public void testDeleteCategory() {
        int id = 1;
        Category category = restTemplate.getForObject(getRootUrl() + "/category/" + id, Category.class);
        assertNotNull(category);
        restTemplate.delete(getRootUrl() + "/category/" + id);
        try {
            restTemplate.getForObject(getRootUrl() + "/category/" + id, Category.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
