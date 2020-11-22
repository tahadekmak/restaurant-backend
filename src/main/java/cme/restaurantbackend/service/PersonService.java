package cme.restaurantbackend.service;

import cme.restaurantbackend.MyFunctions;
import cme.restaurantbackend.ResourceNotFoundException;
import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.repository.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository repo;

    public List<Person> getAllPersons() {
        return repo.findAll();
    }


    public Person getPersonById(long personId) throws ResourceNotFoundException {

        Optional<Person> personDb = repo.findById(personId);

        if (personDb.isPresent()) {
            return personDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + personId);
        }
    }

    public Person createPerson(Person person) {
        return repo.save(person);
    }

    public Person updatePerson(Person person) throws ResourceNotFoundException {
        Optional <Person> personDb = repo.findById(person.getId());

        if (personDb.isPresent()) {
            Person personUpdate = personDb.get();
            personUpdate.setId(person.getId());
            personUpdate.setFirstName(person.getFirstName());
            personUpdate.setLastName(person.getLastName());
            repo.save(personUpdate);
            return personUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + person.getId());
        }
    }

    public void deletePerson(long personId) throws ResourceNotFoundException {
        Optional <Person> personDb = repo.findById(personId);

        if (personDb.isPresent()) {
            repo.delete(personDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + personId);
        }
    }

    public void initPerson() {
        try {
            File file = MyFunctions.getFileFromResource("PERSONS_DATA.json");
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Person> resList = objectMapper.readValue(
                    file,
                    new TypeReference<>() {
                    });

            resList.forEach(x -> {
                Person person = new Person();
                person.setFirstName(x.getFirstName());
                person.setLastName(x.getLastName());

                repo.save(person);
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}