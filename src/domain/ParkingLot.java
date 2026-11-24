package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkingLot implements Serializable {
    
    private static final int TOTAL_SPACES = 22;

    private String name;
    private String address;
    private int totalSpaces;

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Receipt> receipts;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Customer> customers;

    public ParkingLot(String newName, String newAddress) {
        setName(newName);
        setAddress(newAddress);
        this.totalSpaces = TOTAL_SPACES;
        this.vehicles = new ArrayList<>();
        this.receipts = new ArrayList<>();
        this.parkingSpots = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void setName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("The name can't be empty");
        }
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String newAddress) {
        if (newAddress == null || newAddress.isBlank()) {
            throw new IllegalArgumentException("The address can't be empty");
        }
        this.address = newAddress;
    }

    public String getAddress() {
        return address;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public ArrayList<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}