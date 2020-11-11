package cme.restaurantbackend.model;

import java.util.Date;

public class VisitData {

    private String restaurantName;
    private Date visitDate;

    public VisitData() {

    }

    public VisitData(String restaurantName, Date visitDate) {
        this.restaurantName = restaurantName;
        this.visitDate = visitDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
