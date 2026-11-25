package domain;

import java.io.Serializable;

// Represents a parking receipt generated when a vehicle checks out
public class Receipt implements Serializable {

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private int hours;
    private double totalAmount;
    private String issueDate;
    private boolean isCancelled;

    // Creates an active receipt and calculates the total parking fee
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

    // Returns the receipt ID
    public int getId() {
        return id;
    }

    // Validates and sets a positive receipt ID
    private void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be a positive number");
        }
        this.id = id;
    }

    // Returns the customer billed on this receipt
    public Customer getCustomer() {
        return customer;
    }

    // Validates and sets the customer associated with this receipt
    private void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The customer cannot be null");
        }
        this.customer = customer;
    }

    // Returns the vehicle associated with this receipt
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Validates and sets the vehicle associated with this receipt
    private void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The vehicle cannot be null");
        }
        this.vehicle = vehicle;
    }

    // Returns the parking spot used during the stay
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    // Validates and sets the parking spot associated with this receipt
    private void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) {
            throw new IllegalArgumentException("The parking spot cannot be null");
        }
        this.parkingSpot = parkingSpot;
    }

    // Returns the number of billed hours
    public int getHours() {
        return hours;
    }

    // Validates and updates the billed hours, recalculating the total amount if a vehicle is set
    public void setHours(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("The hours must be greater than zero");
        }
        this.hours = hours;
        if (this.vehicle != null) {
            calculateTotalAmount();
        }
    }

    // Returns the calculated total amount to pay
    public double getTotalAmount() {
        return totalAmount;
    }

    // Calculates and stores the total amount based on the vehicle's hourly rate and hours parked
    public double calculateTotalAmount() {
        if (this.vehicle == null) {
            throw new IllegalStateException("Cannot calculate total amount without a vehicle");
        }
        this.totalAmount = this.vehicle.getHourlyRate() * this.hours;
        return totalAmount;
    }

    // Returns the issue date of the receipt
    public String getIssueDate() {
        return issueDate;
    }

    // Validates and sets a non-empty issue date
    private void setIssueDate(String issueDate) {
        if (issueDate == null || issueDate.isBlank()) {
            throw new IllegalArgumentException("The issue date cannot be empty");
        }
        this.issueDate = issueDate;
    }

    // Returns whether the receipt has been cancelled
    public boolean isCancelled() {
        return isCancelled;
    }

    // Updates the cancellation status, preventing cancelling an already cancelled receipt
    public void setCancelled(boolean isCancelled) {
        if (this.isCancelled && isCancelled) {
            throw new IllegalStateException("The receipt is already cancelled");
        }
        this.isCancelled = isCancelled;
    }

    // Returns a formatted summary with all receipt details and current status
    public String getReceiptDetails() {
        String status;

        if (isCancelled) {
            status = "CANCELLED";
        } else {
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
