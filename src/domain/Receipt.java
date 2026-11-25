package domain;

import java.io.Serializable;

// Represents a parking receipt generated when a vehicle checks out[span_0](start_span)[span_0](end_span)
public class Receipt implements Serializable {

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private int hours;
    private double totalAmount;
    private String issueDate;
    private boolean isCancelled;

    // Creates an active receipt and calculates the total parking fee[span_1](start_span)[span_1](end_span)
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

    // Returns the receipt ID[span_2](start_span)[span_2](end_span)
    public int getId() {
        return id;
    }

    // Validates and sets a positive receipt ID[span_3](start_span)[span_3](end_span)
    private void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be a positive number");
        }
        this.id = id;
    }

    // Returns the customer billed on this receipt[span_4](start_span)[span_4](end_span)
    public Customer getCustomer() {
        return customer;
    }

    // Validates and sets the customer associated with this receipt[span_5](start_span)[span_5](end_span)
    private void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The customer cannot be null");
        }
        this.customer = customer;
    }

    // Returns the vehicle associated with this receipt[span_6](start_span)[span_6](end_span)
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Validates and sets the vehicle associated with this receipt[span_7](start_span)[span_7](end_span)
    private void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The vehicle cannot be null");
        }
        this.vehicle = vehicle;
    }

    // Returns the parking spot used during the stay[span_8](start_span)[span_8](end_span)
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    // Validates and sets the parking spot associated with this receipt[span_9](start_span)[span_9](end_span)
    private void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) {
            throw new IllegalArgumentException("The parking spot cannot be null");
        }
        this.parkingSpot = parkingSpot;
    }

    // Returns the number of billed hours[span_10](start_span)[span_10](end_span)
    public int getHours() {
        return hours;
    }

    // Validates and updates the billed hours, recalculating the total amount if a vehicle is set[span_11](start_span)[span_11](end_span)
    public void setHours(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("The hours must be greater than zero");
        }
        this.hours = hours;
        if (this.vehicle != null) {
            calculateTotalAmount();
        }
    }

    // Returns the calculated total amount to pay[span_12](start_span)[span_12](end_span)
    public double getTotalAmount() {
        return totalAmount;
    }

    // Calculates and stores the total amount based on the vehicle's hourly rate and hours parked[span_13](start_span)[span_13](end_span)
    public double calculateTotalAmount() {
        if (this.vehicle == null) {
            throw new IllegalStateException("Cannot calculate total amount without a vehicle");
        }
        this.totalAmount = this.vehicle.getHourlyRate() * this.hours;
        return totalAmount;
    }

    // Returns the issue date of the receipt[span_14](start_span)[span_14](end_span)
    public String getIssueDate() {
        return issueDate;
    }

    // Validates and sets a non-empty issue date[span_15](start_span)[span_15](end_span)
    private void setIssueDate(String issueDate) {
        if (issueDate == null || issueDate.isBlank()) {
            throw new IllegalArgumentException("The issue date cannot be empty");
        }
        this.issueDate = issueDate;
    }

    // Returns whether the receipt has been cancelled[span_16](start_span)[span_16](end_span)
    public boolean isCancelled() {
        return isCancelled;
    }

    // Updates the cancellation status, preventing cancelling an already cancelled receipt[span_17](start_span)[span_17](end_span)
    public void setCancelled(boolean isCancelled) {
        if (this.isCancelled && isCancelled) {
            throw new IllegalStateException("The receipt is already cancelled");
        }
        this.isCancelled = isCancelled;
    }

    // Returns a formatted summary with all receipt details and current status[span_18](start_span)[span_18](end_span)
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
