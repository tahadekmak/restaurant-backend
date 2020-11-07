package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/visit")
    public List<Person> getAllEmployees() {
        return personRepository.findAll();
    }

    @GetMapping("/visit/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personID)
            throws ResourceNotFoundException {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/visit")
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<Person> updateEmployee(@PathVariable(value = "id") Long personID,
                                                 @Valid @RequestBody Person personDetails) throws ResourceNotFoundException {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));

        person.setLastName(personDetails.getLastName());
        person.setFirstName(personDetails.getFirstName());
        final Person updatedEmployee = personRepository.save(person);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/visit/{id}")
    public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long PersonID)
            throws ResourceNotFoundException {
        Person person = personRepository.findById(PersonID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonID));

        personRepository.delete(person);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}