package cme.restaurantbackend.model;

import javax.persistence.*;
import java.util.Date;

public class VisitAbstraction {

    private Long personID;
    private Long restaurantID;
    private String date;

    public VisitAbstraction() {

    }

    public VisitAbstraction(Long personID, Long restaurantID, String date) {
        this.personID = personID;
        this.restaurantID = restaurantID;
        this.date = date;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
