package cme.restaurantbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visit")
public class Visit {

    private Long id;
    private Person person;
    private Restaurant restaurant;
    private Date date;

    public Visit() {

    }

    public Visit(Person person, Restaurant restaurant, Date date) {
        this.person = person;
        this.restaurant = restaurant;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_generator")
    @SequenceGenerator(name="visit_generator", sequenceName = "visit_seq", allocationSize=50)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Column(name = "visit_date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
