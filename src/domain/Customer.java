package domain;

import java.io.Serializable;
import java.util.ArrayList;

// Represents a customer who owns vehicles and uses the parking lot
public class Customer implements Serializable {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private boolean active;
    private ArrayList<Vehicle> vehicles;

    // Creates an active customer with validated personal and contact details
    public Customer(int id, String name, String phoneNumber, String email) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.vehicles = new ArrayList<>();
        this.active = true;
    }

    // Returns a text summary of the customer and their registered vehicles
    @Override
    public String toString() {
        return "Customer [ID=" + id +
                ", Name=" + name +
                ", Phone=" + phoneNumber +
                ", Email=" + email +
                ", Active=" + (active ? "Yes" : "No") +
                ", Vehicles registered=" + vehicles.size() + "]";
    }

    // Associates a vehicle with this customer
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException(" vehicle can't be null");
        }
        this.vehicles.add(vehicle);
    }

    // Returns the list of vehicles registered under this customer
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    // Returns whether the customer account is currently active
    public boolean isActive() {
        return active;
    }

    // Marks the customer account as active
    public void setActive() {
        this.active = true;
    }

    // Deactivates the customer account without removing historical data
    public void setDeactivate() {
        this.active = false;
    }

    // Validates and sets the customer's email address
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email must contain '@' and a domain extension");
        }
        this.email = email.trim();
    }

    // Returns the customer's email address
    public String getEmail() {
        return email;
    }

    // Validates and sets the customer's numeric phone number
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank() || !phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException(" Phone number can't be blank or a negative number");
        }
        this.phoneNumber = phoneNumber;
    }

    // Returns the customer's phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Validates and sets the customer's name
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(" customer name can't be empty ");
        }
        this.name = name;
    }

    // Returns the customer's name
    public String getName() {
        return name;
    }

    // Validates and sets the customer's positive ID
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The ID of the customer must be a positive integer");
        }
        this.id = id;
    }

    // Returns the customer's ID
    public int getId() {
        return id;
    }
}
