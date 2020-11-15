package cme.restaurantbackend.model;

public class CategoryAbstraction {

    private String name;

    public CategoryAbstraction() {

    }

    public CategoryAbstraction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}