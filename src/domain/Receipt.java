package domain;

import java.io.Serializable;

public class Receipt implements Serializable {

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private int hours;
    private double totalAmount;
    private String issueDate;
    private boolean isCancelled;

    public Receipt(int id, Customer customer, Vehicle vehicle, ParkingSpot parkingSpot, int hours, String issueDate) {
        setId(id);
        setCustomer(customer);
        setVehicle(vehicle);
        setParkingSpot(parkingSpot);
        setHours(hours);
        setIssueDate(issueDate);
        this.isCancelled = false;
        calculateTotalAmount();
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be a positive number");
        }
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    private void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The customer cannot be null");
        }
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    private void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The vehicle cannot be null");
        }
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    private void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) {
            throw new IllegalArgumentException("The parking spot cannot be null");
        }
        this.parkingSpot = parkingSpot;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("The hours must be greater than zero");
        }
        this.hours = hours;
        if (this.vehicle != null) {
            calculateTotalAmount();
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double calculateTotalAmount() {
        if (this.vehicle == null) {
            throw new IllegalStateException("Cannot calculate total amount without a vehicle");
        }
        this.totalAmount = this.vehicle.getHourlyRate() * this.hours;
        return totalAmount;
    }

    public String getIssueDate() {
        return issueDate;
    }

    private void setIssueDate(String issueDate) {
        if (issueDate == null || issueDate.isBlank()) {
            throw new IllegalArgumentException("The issue date cannot be empty");
        }
        this.issueDate = issueDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        if (this.isCancelled && isCancelled) {
            throw new IllegalStateException("The receipt is already cancelled");
        }
        this.isCancelled = isCancelled;
    }

    public String getReceiptDetails() {
        String status;
        
        if (isCancelled) {
            status = "CANCELLED";
        } 
        else {
            status = "ACTIVE";
        }

        return "Receipt #" + id
                + " | Status: " + status
                + " | Date: " + issueDate
                + " | Customer: " + customer.getName()
                + " | Vehicle: " + this.vehicle.getVehicleType() + " (" + this.vehicle.getVehiclePlate() + ")"
                + " | Spot: #" + this.parkingSpot.getSpaceNumber()
                + " | Hours: " + hours
                + " | Total Amount: $" + totalAmount;
    }
}
