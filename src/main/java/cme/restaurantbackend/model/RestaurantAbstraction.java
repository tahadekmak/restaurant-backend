package cme.restaurantbackend.model;

public class RestaurantAbstraction {

    private String name;
    private double averageCost;
    private String address;
    private String phoneNumber;
    private String image;
    private Long categoryID;

    public RestaurantAbstraction() {

    }

    public RestaurantAbstraction(String name, double averageCost, String address, String phoneNumber, String image, Long categoryID) {
        this.name = name;
        this.averageCost = averageCost;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }


    @Override
    public String toString() {
        return "RestaurantAbstraction{" +
                ", name='" + name + '\'' +
                ", category='" + categoryID + '\'' +
                ", averageCost=" + averageCost +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}