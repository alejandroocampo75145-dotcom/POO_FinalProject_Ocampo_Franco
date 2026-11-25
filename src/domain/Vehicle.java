package domain;

import java.io.Serializable;

// Abstract base class representing any vehicle stored in the parking lot[span_0](start_span)[span_0](end_span)
public abstract class Vehicle implements Serializable {

    private String vehiclePlate;
    private String brand;
    private Customer owner;

    // Initializes a vehicle with validated plate, owner, and brand[span_1](start_span)[span_1](end_span)
    public Vehicle(String newVehiclePlate, String newBrand, Customer newOwner) {
        setVehiclePlate(newVehiclePlate);
        setOwner(newOwner);
        setBrand(newBrand);
    }

    // Validates and sets the vehicle's license or identifier plate[span_2](start_span)[span_2](end_span)
    public void setVehiclePlate(String newVehiclePlate) {
        if (newVehiclePlate == null || newVehiclePlate.isBlank()) {
            throw new IllegalArgumentException("The plate of the vehicle can't be empty");
        }
        this.vehiclePlate = newVehiclePlate;
    }

    // Returns the vehicle's plate[span_3](start_span)[span_3](end_span)
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    // Validates and sets the vehicle's brand[span_4](start_span)[span_4](end_span)
    public void setBrand(String newBrand) {
        if (newBrand == null || newBrand.isBlank()) {
            throw new IllegalArgumentException("The brand of the vehicle can't be empty");
        }
        this.brand = newBrand;
    }

    // Returns the vehicle's brand[span_5](start_span)[span_5](end_span)
    public String getBrand() {
        return brand;
    }

    // Validates and sets the customer who owns this vehicle[span_6](start_span)[span_6](end_span)
    public void setOwner(Customer newOwner) {
        if (newOwner == null) {
            throw new IllegalArgumentException("The class of Customer can't be null");
        }
        this.owner = newOwner;
    }

    // Returns the customer who owns this vehicle[span_7](start_span)[span_7](end_span)
    public Customer getOwner() {
        return owner;
    }

    // Returns the hourly parking fee for this vehicle type[span_8](start_span)[span_8](end_span)
    public abstract double getHourlyRate();

    // Returns the vehicle type identifier[span_9](start_span)[span_9](end_span)
    public abstract String getVehicleType();
}


