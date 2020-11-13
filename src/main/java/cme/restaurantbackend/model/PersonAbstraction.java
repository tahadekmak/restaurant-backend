package cme.restaurantbackend.model;

public class PersonAbstraction {

    private String firstName;
    private String lastName;

    public PersonAbstraction() {

    }

    public PersonAbstraction(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}