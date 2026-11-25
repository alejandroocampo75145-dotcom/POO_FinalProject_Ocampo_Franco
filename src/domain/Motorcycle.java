package domain;

import java.io.Serializable;

// Represents a motorcycle in the parking lot system
public class Motorcycle extends Vehicle implements Serializable {

    // Creates a motorcycle with its plate, brand, and owner
    public Motorcycle(String newVehiclePlate, String newBrand, Customer newOwner) {
        super(newVehiclePlate, newBrand, newOwner);
    }

    // Returns the hourly parking rate for a motorcycle
    @Override
    public double getHourlyRate() {
        return 2500.00;
    }

    // Returns the vehicle type identifier for a motorcycle
    @Override
    public String getVehicleType() {
        return "MOTORCYCLE";
    }
}
