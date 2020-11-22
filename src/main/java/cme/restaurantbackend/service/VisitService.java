package cme.restaurantbackend.service;

import cme.restaurantbackend.MyFunctions;
import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.dto.VisitBrief;
import cme.restaurantbackend.model.Category;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.model.Visit;
import cme.restaurantbackend.repository.CategoryRepository;
import cme.restaurantbackend.repository.PersonRepository;
import cme.restaurantbackend.repository.RestaurantRepository;
import cme.restaurantbackend.repository.VisitRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private PersonRepository personRepository;

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(long visitId) throws ResourceNotFoundException {

        Optional<Visit> visitDb = visitRepository.findById(visitId);

        if (visitDb.isPresent()) {
            return visitDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + visitId);
        }
    }

    public List<VisitBrief> getVisitsByPersonId(long personId) {

        return visitRepository.findByPersonId(personId);
    }

    public Visit createVisit(long personId, long restaurantId, Visit visit) throws ResourceNotFoundException {

        Optional <Person> personDb = personRepository.findById(personId);
        if(personDb.isPresent()) {

            Optional <Restaurant> restaurantDb = restaurantRepository.findById(restaurantId);
            if(restaurantDb.isPresent()) {

                visit.setPerson(personDb.get());
                visit.setRestaurant(restaurantDb.get());
                //visit.setDate(MyFunctions.stringToDate(date));
                return visitRepository.save(visit);
            }
            else {
                throw new ResourceNotFoundException("Record not found with id : " + restaurantId);
            }
        }
        else {
            throw new ResourceNotFoundException("Record not found with id : " + personId);
        }
    }

    public Visit updateVisit(long visitId, long personId, long restaurantId, Visit visit) throws ResourceNotFoundException {
        Optional <Visit> visitDb = visitRepository.findById(visitId);

        if (visitDb.isPresent()) {

            Optional <Restaurant> restaurantDb = restaurantRepository.findById(restaurantId);
            if(restaurantDb.isPresent()) {

                Optional <Person> personDb = personRepository.findById(personId);
                if(personDb.isPresent()) {

                    visit.setPerson(personDb.get());
                    visit.setRestaurant(restaurantDb.get());
                    //visit.setDate(MyFunctions.stringToDate(date));
                    return visitRepository.save(visit);
                }
                else {
                    throw new ResourceNotFoundException("Record not found with id : " + personId);
                }
            }

            else {
                throw new ResourceNotFoundException("Record not found with id : " + restaurantId);
            }

        } else {
            throw new ResourceNotFoundException("Record not found with id : " + visitId);
        }
    }

    public void deleteVisit(long visitId) throws ResourceNotFoundException {
        Optional <Visit> visitDb = visitRepository.findById(visitId);

        if (visitDb.isPresent()) {
            visitRepository.delete(visitDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + visitId);
        }
    }
}