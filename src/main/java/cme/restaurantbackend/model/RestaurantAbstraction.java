package cme.restaurantbackend.model;

public class RestaurantAbstraction {

    private String name;
    private String category;
    private double averageCost;
    private String address;
    private String phoneNumber;
    private String image;

    public RestaurantAbstraction() {

    }

    public RestaurantAbstraction(String name, String category, double averageCost, String address, String phoneNumber, String image) {
        this.name = name;
        this.category = category;
        this.averageCost = averageCost;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "RestaurantAbstraction{" +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", averageCost=" + averageCost +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}