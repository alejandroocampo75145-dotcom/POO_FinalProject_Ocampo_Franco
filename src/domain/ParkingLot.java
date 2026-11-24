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
    initializeSpots();
}

// Initializes the 22 fixed parking spots with their assigned vehicle types
    private void initializeSpots() {
        for (int i = 0; i < TOTAL_SPACES; i++) {
            if (i < 6) {
                this.parkingSpots.add(new ParkingSpot(i, "Car"));
            } else if (i < 12) {
                this.parkingSpots.add(new ParkingSpot(i, "Motorcycle"));
            } else if (i < 17) {
                this.parkingSpots.add(new ParkingSpot(i, "Truck"));
            } 
            else {
                this.parkingSpots.add(new ParkingSpot(i, "Bicycle"));
        }
    }
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

    public void addCustomer(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException(" Customer can not be null");
        }
        if(findCustomerById(customer.getId()) != null){
            throw new IllegalArgumentException("Can not register an existent customer");
        }
        this.customers.add(customer);

    }

    public Customer findCustomerById(int id){
        for (Customer customer: customers){
            if (customer.getId() == id)
                return customer;
        }
        return null;
    }

    public void updateCustomerInformation(int id, String newPhoneNumber, String newEmail){
        Customer customer = findCustomerById(id);
        if(customer == null){
            throw new IllegalArgumentException("Customer with id " + id + "was not found");
        }
        customer.setEmail(newEmail);
        customer.setPhoneNumber(newPhoneNumber);
    }
    public void deActivateCustomer(int id){
        Customer customer = findCustomerById(id);
        if (customer == null){
            throw new IllegalArgumentException("Customer with id " + id + "was not found");
        }
        if(!customer.isActive()){
            throw new IllegalArgumentException("The customer is already inactive");
        }

        customer.setDeactivate();
    }
}