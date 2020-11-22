package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public ResponseEntity <List<Person>> getAllPersons() {
        return ResponseEntity.ok().body(personService.getAllPersons());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(personService.getPersonById(id));
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        return ResponseEntity.ok().body(personService.createPerson(person));
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id,
                                               @Valid @RequestBody Person person) throws ResourceNotFoundException {
        person.setId(id);
        return ResponseEntity.ok().body(personService.updatePerson(person));
    }

    @DeleteMapping("/person/{id}")
    public HttpStatus deletePerson(@PathVariable long id) throws ResourceNotFoundException {
        personService.deletePerson(id);
        return HttpStatus.OK;
    }

    @PostMapping("/initPerson")
    public HttpStatus initPerson() {
        personService.initPerson();
        return HttpStatus.OK;
    }
}