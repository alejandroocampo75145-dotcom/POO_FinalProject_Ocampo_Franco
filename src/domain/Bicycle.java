package domain;

import java.io.Serializable;

// Represents a bicycle in the parking lot system
public class Bicycle extends Vehicle implements Serializable {

    // Creates a bicycle with a generated plate based on the owner's ID
    public Bicycle(String newBrand, Customer newOwner) {
        if (newOwner == null) {
            throw new IllegalArgumentException("The owner can't be null");
        }
        super("BIKE-" + newOwner.getId(), newBrand, newOwner);
    }

    // Returns the hourly parking rate for a bicycle
    @Override
    public double getHourlyRate() {
        return 1500.0;
    }

    // Returns the vehicle type identifier for a bicycle
    @Override
    public String getVehicleType() {
        return "BICYCLE";
    }
}
