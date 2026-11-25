package domain;

import java.io.Serializable;

// Represents a car in the parking lot system
public class Car extends Vehicle implements Serializable {

    // Creates a car with its plate, brand, and owner
    public Car(String newVehiclePlate, String newBrand, Customer newOwner) {
        super(newVehiclePlate, newBrand, newOwner);
    }

    // Returns the hourly parking rate for a car
    @Override
    public double getHourlyRate() {
        return 5000.00;
    }

    // Returns the vehicle type identifier for a car
    @Override
    public String getVehicleType() {
        return "CAR";
    }
}