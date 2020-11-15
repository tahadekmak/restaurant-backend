package cme.restaurantbackend.model;

import java.util.Arrays;

public class RestaurantData {

    private String name;
    private double averageCost;
    private String address;
    private String phoneNumber;
    private byte[] image;
    private String category;

    public RestaurantData() {

    }

    public RestaurantData(String name, double averageCost, String address, String phoneNumber, byte[] image, String category) {
        this.name = name;
        this.averageCost = averageCost;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.category = category;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RestaurantData{" +
                "name='" + name + '\'' +
                ", averageCost=" + averageCost +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image=" + Arrays.toString(image) +
                ", category='" + category + '\'' +
                '}';
    }
}