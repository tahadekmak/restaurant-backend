package cme.restaurantbackend.model;

import java.time.OffsetDateTime;

public class VisitData {

    private Long visitID;
    private String restaurantName;
    private OffsetDateTime visitDate;

    public VisitData() {

    }

    public VisitData(Long visitID, String restaurantName, OffsetDateTime visitDate) {
        this.visitID = visitID;
        this.restaurantName = restaurantName;
        this.visitDate = visitDate;
    }

    public Long getVisitID() {
        return visitID;
    }

    public void setVisitID(Long visitID) {
        this.visitID = visitID;
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
