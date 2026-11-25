package domain;

import java.io.Serializable;

// Represents a truck in the parking lot system
public class Truck extends Vehicle implements Serializable {

    // Creates a truck with its plate, brand, and owner
    public Truck(String newVehiclePlate, String newBrand, Customer newOwner) {
        super(newVehiclePlate, newBrand, newOwner);
    }

    // Returns the hourly parking rate for a truck
    @Override
    public double getHourlyRate() {
        return 8000.00;
    }

    // Returns the vehicle type identifier for a truck
    @Override
    public String getVehicleType() {
        return "TRUCK";
    }
}
