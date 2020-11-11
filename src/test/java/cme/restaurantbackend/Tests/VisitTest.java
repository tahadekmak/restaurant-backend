package cme.restaurantbackend.Tests;

import cme.restaurantbackend.RestaurantBackendApplication;
import cme.restaurantbackend.model.Visit;
import cme.restaurantbackend.model.VisitAbstraction;
import cme.restaurantbackend.model.VisitData;
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
public class VisitTest {
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
    public void testGetAllVisits() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/visit",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetVisitsByPersonId() {
        VisitData visitData = restTemplate.getForObject(getRootUrl() + "/visitsByPersonId/1", VisitData.class);
        System.out.println(visitData.getRestaurantName());
        assertNotNull(visitData);
    }

    @Test
    public void testGetVisitById() {
        Visit visit = restTemplate.getForObject(getRootUrl() + "/visit/1", Visit.class);
        System.out.println(visit.getDate());
        assertNotNull(visit);
    }

    @Test
    public void testCreateVisit() {
        VisitAbstraction visitData = new VisitAbstraction();
        visitData.setPersonID(1L);
        visitData.setRestaurantID(1L);
        visitData.setDate("5/5/2020");
        ResponseEntity<Visit> postResponse = restTemplate.postForEntity(getRootUrl() + "/visit", visitData, Visit.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateVisit() {
        int id = 1;
        VisitAbstraction visitData = new VisitAbstraction();
        visitData.setPersonID(1L);
        visitData.setRestaurantID(1L);
        visitData.setDate("8/5/2020");
        restTemplate.put(getRootUrl() + "/visit/" + id, visitData);
        Visit updatedVisit = restTemplate.getForObject(getRootUrl() + "/visit/" + id, Visit.class);
        assertNotNull(updatedVisit);
    }

    @Test
    public void testDeleteVisit() {
        int id = 1;
        Visit visit = restTemplate.getForObject(getRootUrl() + "/visit/" + id, Visit.class);
        assertNotNull(visit);
        restTemplate.delete(getRootUrl() + "/visit/" + id);
        try {
            visit = restTemplate.getForObject(getRootUrl() + "/visit/" + id, Visit.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
