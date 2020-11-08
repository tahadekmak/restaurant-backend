package cme.restaurantbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    private Long id;
    private String name;
    private String type;
    private double averageCost;
    private String address;
    private String phoneNumber;
    private byte[] image;
    private Set<Visit> visits = new HashSet<Visit>();

    public Restaurant() {

    }

    public Restaurant(Long id, String name, String type, double averageCost, String address, String phoneNumber, byte[] image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.averageCost = averageCost;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    @Id
    @Column(name = "restaurant_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_generator")
    @SequenceGenerator(name="restaurant_generator", sequenceName = "restaurant_seq", allocationSize=50)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "average_cost", nullable = false)
    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Lob
    @Column(name = "image", nullable = true)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Restaurant";
    }
}
