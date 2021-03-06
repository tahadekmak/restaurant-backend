package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.dto.VisitBrief;
import cme.restaurantbackend.model.*;

import cme.restaurantbackend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/visit")
    public ResponseEntity<List<Visit>> getAllVisits() {
        return ResponseEntity.ok().body(visitService.getAllVisits());
    }

    @GetMapping("/visit/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable(value = "id") long visitId)
            throws ResourceNotFoundException {

        return ResponseEntity.ok().body(visitService.getVisitById(visitId));
    }

    @GetMapping("/visitsByPersonId/{person_id}")
    public ResponseEntity<List<VisitBrief>> getVisitsByPersonId(@PathVariable(value = "person_id") long personId) {
        return ResponseEntity.ok().body(visitService.getVisitsByPersonId(personId));
    }

    @PostMapping("/visit/person/{person_id}/restaurant/{restaurant_id}")
    public ResponseEntity<Visit> createVisit(@PathVariable(value = "person_id") long personId,
                             @PathVariable(value = "restaurant_id") long restaurantId,
                             @Valid @RequestBody Visit visit) throws ResourceNotFoundException {

        return ResponseEntity.ok().body(visitService.createVisit(personId, restaurantId, visit));
    }

    @PutMapping("/visit/{id}/person/{person_id}/restaurant/{restaurant_id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable(value = "id") long visitId,
                                                  @PathVariable(value = "person_id") long personId,
                                                  @PathVariable(value = "restaurant_id") long restaurantId,
                                                       @Valid @RequestBody Visit visit) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(visitService.updateVisit(visitId, personId, restaurantId, visit));
    }

    @DeleteMapping("/visit/{id}")
    public HttpStatus deleteVisit(@PathVariable(value = "id") Long visitId) throws ResourceNotFoundException {
        visitService.deleteVisit(visitId);
        return HttpStatus.OK;
    }
}