package cme.restaurantbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<Visit> visits = new HashSet<Visit>();

    public Person() {

    }

    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @Column(name = "person_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name="person_generator", sequenceName = "person_seq", allocationSize=50)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "person_fname", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "person_lname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "person")
    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
}