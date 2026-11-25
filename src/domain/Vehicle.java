package domain;

import java.io.Serializable;

// Abstract base class representing any vehicle stored in the parking lot
public abstract class Vehicle implements Serializable {

    private String vehiclePlate;
    private String brand;
    private Customer owner;

    // Initializes a vehicle with validated plate, owner, and brand
    public Vehicle(String newVehiclePlate, String newBrand, Customer newOwner) {
        setVehiclePlate(newVehiclePlate);
        setOwner(newOwner);
        setBrand(newBrand);
    }

    // Validates and sets the vehicle's license or identifier plate
    public void setVehiclePlate(String newVehiclePlate) {
        if (newVehiclePlate == null || newVehiclePlate.isBlank()) {
            throw new IllegalArgumentException("The plate of the vehicle can't be empty");
        }
        this.vehiclePlate = newVehiclePlate;
    }

    // Returns the vehicle's plate
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    // Validates and sets the vehicle's brand
    public void setBrand(String newBrand) {
        if (newBrand == null || newBrand.isBlank()) {
            throw new IllegalArgumentException("The brand of the vehicle can't be empty");
        }
        this.brand = newBrand;
    }

    // Returns the vehicle's brand
    public String getBrand() {
        return brand;
    }

    // Validates and sets the customer who owns this vehicle
    public void setOwner(Customer newOwner) {
        if (newOwner == null) {
            throw new IllegalArgumentException("The class of Customer can't be null");
        }
        this.owner = newOwner;
    }

    // Returns the customer who owns this vehicle
    public Customer getOwner() {
        return owner;
    }

    // Returns the hourly parking fee for this vehicle type
    public abstract double getHourlyRate();

    // Returns the vehicle type identifier
    public abstract String getVehicleType();

    // Returns a formatted summary of the vehicle details
    @Override
    public String toString() {
        return getVehicleType()
                + " [Plate=" + vehiclePlate
                + ", Brand=" + brand
                + ", Owner=" + owner.getName()
                + ", Rate=$" + getHourlyRate() + "/h]";
    }
}
