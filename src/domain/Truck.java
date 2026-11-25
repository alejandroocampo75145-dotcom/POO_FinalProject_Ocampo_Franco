package domain;

import java.io.Serializable;

// Represents a truck in the parking lot system[span_0](start_span)[span_0](end_span)
public class Truck extends Vehicle implements Serializable {

    // Creates a truck with its plate, brand, and owner[span_1](start_span)[span_1](end_span)
    public Truck(String newVehiclePlate, String newBrand, Customer newOwner) {
        super(newVehiclePlate, newBrand, newOwner);
    }

    // Returns the hourly parking rate for a truck[span_2](start_span)[span_2](end_span)
    @Override
    public double getHourlyRate() {
        return 8000.00;
    }

    // Returns the vehicle type identifier for a truck[span_3](start_span)[span_3](end_span)
    @Override
    public String getVehicleType() {
        return "TRUCK";
    }
}
