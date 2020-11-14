package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.*;
import cme.restaurantbackend.repository.PersonRepository;
import cme.restaurantbackend.repository.RestaurantRepository;
import cme.restaurantbackend.repository.VisitRepository;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
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

    @GetMapping("/visitsByPersonId/{id}")
    public List<VisitData> getVisitsByPersonId(@PathVariable(value = "id") Long personId) {
        return visitRepository.findByPersonId(personId);
    }

    @GetMapping("/visit/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable(value = "id") Long VisitID)
            throws ResourceNotFoundException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found for this id :: " + VisitID));
        return ResponseEntity.ok().body(visit);
    }

    @PostMapping("/visit")
    public Visit createVisit(@Valid @RequestBody VisitAbstraction visitAbstraction) throws ResourceNotFoundException, ParseException {

        Long personID = visitAbstraction.getPersonID();
        Long restaurantID = visitAbstraction.getRestaurantID();
        String stringDate = visitAbstraction.getDate();

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
                .ofPattern("dd/MM/uuuu'T'HH:mm:ss:SSSXXXXX");

        OffsetDateTime odtInstanceAtOffset = OffsetDateTime.parse(stringDate, DATE_TIME_FORMATTER);
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));
        Visit visit = new Visit(person, restaurant, odtInstanceAtOffset);

        return visitRepository.save(visit);
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable(value = "id") Long VisitID,
                                             @Valid @RequestBody VisitAbstraction visitData) throws ResourceNotFoundException, ParseException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found for this id :: " + VisitID));

        Long personID = visitData.getPersonID();
        Long restaurantID = visitData.getRestaurantID();
        String stringDate = visitData.getDate();
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
                .ofPattern("dd/MM/uuuu'T'HH:mm:ss:SSSXXXXX");

        OffsetDateTime odtInstanceAtOffset = OffsetDateTime.parse(stringDate, DATE_TIME_FORMATTER);

        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personID));
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantID));

        visit.setPerson(person);
        visit.setRestaurant(restaurant);
        visit.setDate(odtInstanceAtOffset);

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