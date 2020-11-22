package cme.restaurantbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
public class Visit {

    private Long id;
    private Person person;
    private Restaurant restaurant;

    @JsonFormat(pattern="d/M/uuuu' 'H:m:s")
    private LocalDateTime date;

    public Visit() {

    }

    public Visit(Long id, Person person, Restaurant restaurant, LocalDateTime date) {
        this.id = id;
        this.person = person;
        this.restaurant = restaurant;
        this.date = date;
    }

    @Id
    @Column(name = "visit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_generator")
    @SequenceGenerator(name = "visit_generator", sequenceName = "visit_seq", allocationSize = 50)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "person_id")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Column(name = "visit_date", nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
