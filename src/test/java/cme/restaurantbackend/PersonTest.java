package cme.restaurantbackend;

import cme.restaurantbackend.model.Person;
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
public class PersonTest {
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
    public void testGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/person",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetPersonById() {
        Person person = restTemplate.getForObject(getRootUrl() + "/person/1", Person.class);
        System.out.println(person.getFirstName());
        assertNotNull(person);
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        person.setFirstName("Tom");
        person.setLastName("Cruise");
        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/person", person, Person.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdatePerson() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        person.setFirstName("Tom1");
        person.setLastName("Cruise1");
        restTemplate.put(getRootUrl() + "/person/" + id, person);
        Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(updatedPerson);
    }

    @Test
    public void testDeletePerson() {
        int id = 2;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(person);
        restTemplate.delete(getRootUrl() + "/person/" + id);
        try {
            person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
