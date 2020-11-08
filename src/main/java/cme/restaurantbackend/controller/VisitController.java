package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.model.Visit;
import cme.restaurantbackend.model.VisitData;
import cme.restaurantbackend.repository.PersonRepository;
import cme.restaurantbackend.repository.RestaurantRepository;
import cme.restaurantbackend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @GetMapping("/visit")
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @GetMapping("/visit/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable(value = "id") Long VisitID)
            throws ResourceNotFoundException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found for this id :: " + VisitID));
        return ResponseEntity.ok().body(visit);
    }

    @PostMapping("/visit")
    public Visit createVisit(@Valid @RequestBody VisitData visitData) throws ResourceNotFoundException, ParseException {

        Long personID = visitData.getPersonID();
        Long restaurantID = visitData.getRestaurantID();
        String stringDate = visitData.getDate();

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));
        Visit visit = new Visit(person, restaurant, date);

        return visitRepository.save(visit);
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable(value = "id") Long VisitID,
                                             @Valid @RequestBody VisitData visitData) throws ResourceNotFoundException, ParseException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found for this id :: " + VisitID));

        Long personID = visitData.getPersonID();
        Long restaurantID = visitData.getRestaurantID();
        String stringDate = visitData.getDate();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);

        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));

        visit.setPerson(person);
        visit.setRestaurant(restaurant);
        visit.setDate(date);

        final Visit updatedVisit = visitRepository.save(visit);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/visit/{id}")
    public Map<String, Boolean> deleteVisit(@PathVariable(value = "id") Long VisitID)
            throws ResourceNotFoundException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found for this id :: " + VisitID));

        visitRepository.delete(visit);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}