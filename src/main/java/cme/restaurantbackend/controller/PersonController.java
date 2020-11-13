package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.model.PersonAbstraction;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.model.RestaurantAbstraction;
import cme.restaurantbackend.repository.PersonRepository;
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
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/person")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personID)
            throws ResourceNotFoundException {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/person")
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personID,
                                                 @Valid @RequestBody Person personDetails) throws ResourceNotFoundException {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));

        person.setLastName(personDetails.getLastName());
        person.setFirstName(personDetails.getFirstName());
        final Person updatedEmployee = personRepository.save(person);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/person/{id}")
    public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long PersonID)
            throws ResourceNotFoundException {
        Person person = personRepository.findById(PersonID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonID));

        personRepository.delete(person);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/initPerson")
    public void initPerson() throws IOException {

        try {

            File file = this.getFileFromResource("PERSONS_DATA.json");

            final ObjectMapper objectMapper = new ObjectMapper();
            List<PersonAbstraction> resList = objectMapper.readValue(
                    file,
                    new TypeReference<>() {
                    });

            resList.forEach(x -> {
                System.out.println(x.toString());
                Person person = new Person();
                person.setFirstName(x.getFirstName());
                person.setLastName(x.getLastName());

                personRepository.save(person);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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