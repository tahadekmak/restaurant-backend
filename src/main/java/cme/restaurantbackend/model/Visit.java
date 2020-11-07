package cme.restaurantbackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @MapsId("personID")
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    Date date;

    public Visit() {

    }

    public Visit(Long id, Person person, Restaurant restaurant, Date date) {
        this.id = id;
        this.person = person;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
