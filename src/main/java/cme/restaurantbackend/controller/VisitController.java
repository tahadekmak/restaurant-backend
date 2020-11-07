package cme.restaurantbackend.controller;

import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Visit;
import cme.restaurantbackend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

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
    public Visit createVisit(@Valid @RequestBody Visit visit) {
        return visitRepository.save(visit);
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable(value = "id") Long VisitID,
                                             @Valid @RequestBody Visit visitDetails) throws ResourceNotFoundException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + VisitID));

        visit.setDate(visitDetails.getDate());
        final Visit updatedVisit = visitRepository.save(visit);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/visit/{id}")
    public Map<String, Boolean> deleteVisit(@PathVariable(value = "id") Long VisitID)
            throws ResourceNotFoundException {
        Visit visit = visitRepository.findById(VisitID)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + VisitID));

        visitRepository.delete(visit);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}