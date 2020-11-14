package cme.restaurantbackend.model;

import java.time.OffsetDateTime;

public class VisitData {

    private String restaurantName;
    private OffsetDateTime visitDate;

    public VisitData() {

    }

    public VisitData(String restaurantName, OffsetDateTime visitDate) {
        this.restaurantName = restaurantName;
        this.visitDate = visitDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public OffsetDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(OffsetDateTime visitDate) {
        this.visitDate = visitDate;
    }
}
