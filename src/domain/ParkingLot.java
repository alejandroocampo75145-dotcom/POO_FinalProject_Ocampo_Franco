package domain;

import java.io.Serializable;
import java.util.ArrayList;

// Central controller class that manages the parking lot, spots, customers, vehicles, and receipts
public class ParkingLot implements Serializable {

    // Fixed total number of parking spots in the facility
    private static final int TOTAL_SPACES = 22;

    private String name;
    private String address;
    private int totalSpaces;

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Receipt> receipts;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Customer> customers;

    // Creates a parking lot with validated name and address, initializing all 22 spots
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
            } else {
                this.parkingSpots.add(new ParkingSpot(i, "Bicycle"));
            }
        }
    }

    // Validates and sets the name of the parking lot
    public void setName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("The name can't be empty");
        }
        this.name = newName;
    }

    // Returns the name of the parking lot
    public String getName() {
        return name;
    }

    // Validates and sets the address of the parking lot
    public void setAddress(String newAddress) {
        if (newAddress == null || newAddress.isBlank()) {
            throw new IllegalArgumentException("The address can't be empty");
        }
        this.address = newAddress;
    }

    // Returns the address of the parking lot
    public String getAddress() {
        return address;
    }

    // Returns the total number of parking spots
    public int getTotalSpaces() {
        return totalSpaces;
    }

    // Returns the list of registered vehicles
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    // Returns the historical list of generated receipts
    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    // Returns the list of all parking spots
    public ArrayList<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    // Returns the list of registered customers
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    // Registers a new customer if their ID is unique
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(" Customer can not be null");
        }
        if (findCustomerById(customer.getId()) != null) {
            throw new IllegalArgumentException("Can not register an existent customer");
        }
        this.customers.add(customer);
    }

    // Searches for a customer by their ID
    public Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    // Updates a customer's email and phone number
    public void updateCustomerInformation(int id, String newPhoneNumber, String newEmail) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + id + "was not found");
        }
        customer.setEmail(newEmail);
        customer.setPhoneNumber(newPhoneNumber);
    }

    // Deactivates an active customer without deleting their records
    public void deActivateCustomer(int id) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + id + "was not found");
        }
        if (!customer.isActive()) {
            throw new IllegalArgumentException("The customer is already inactive");
        }

        customer.setDeactivate();
    }

    // Searches for a registered vehicle by its plate.
    public Vehicle findVehicleByPlate(String plate) {
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("The plate cannot be empty");
        }

        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.getVehiclePlate().equalsIgnoreCase(plate.trim())) {
                return vehicle;
            }
        }
        return null;
    }

    // Searches for a receipt by its ID
    public Receipt findReceiptById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be a positive number");
        }

        for (Receipt receipt : this.receipts) {
            if (receipt.getId() == id) {
                return receipt;
            }
        }
        return null;
    }

    // Registers a new vehicle and associates it with its active owner.
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The vehicle cant be null");
        }
        if (findVehicleByPlate(vehicle.getVehiclePlate()) != null) {
            throw new IllegalArgumentException("The plate of this vehicle already exists");
        }
        if (!vehicle.getOwner().isActive() || findCustomerById(vehicle.getOwner().getId()) == null) {
            throw new IllegalArgumentException("The owner of this vehicle isn't active or isn't registered");
        }
        this.vehicles.add(vehicle);
        vehicle.getOwner().addVehicle(vehicle);
    }

    // Recommends the first available parking spot compatible with the vehicle type.
    public ParkingSpot recommendOptimalSpot(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The vehicle can't be null");
        }
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isAvailable() && parkingSpot.getSpotType().equalsIgnoreCase(vehicle.getVehicleType().trim())) {
                return parkingSpot;
            }
        }
        return null;
    }

    // Searches for a parking spot by its space number
    public ParkingSpot findSpotByNumber(int spaceNumber) {
        if (spaceNumber < 0 || spaceNumber >= TOTAL_SPACES) {
            throw new IllegalArgumentException("Space number must be between 0 and " + (TOTAL_SPACES - 1));
        }
        for (ParkingSpot parkingSpot : this.parkingSpots) {
            if (parkingSpot.getSpaceNumber() == spaceNumber) {
                return parkingSpot;
            }
        }
        return null;
    }

    // Parks a registered vehicle in a specific available and compatible parking spot
    public void parkVehicle(String plate, int spaceNumber) {
        Vehicle vehicle = findVehicleByPlate(plate);
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle with plate " + plate + " was not found");
        }
        for (ParkingSpot parkingSpot : this.parkingSpots) {
            if (parkingSpot.isOccupied()) {
                if (parkingSpot.getVehicle().getVehiclePlate().equalsIgnoreCase(plate.trim())) {
                    throw new IllegalArgumentException("This vehicle is already parked in a spot");
                }
            }
        }
        ParkingSpot spot = findSpotByNumber(spaceNumber);
        if (spot == null) {
            throw new IllegalArgumentException("Parking spot #" + spaceNumber + " was not found");
        }
        spot.setVehicle(vehicle);
    }

    // Checks out a parked vehicle, frees its spot, generates the receipt, and saves it in history
    public Receipt checkoutVehicle(String plate, int hours, String issueDate) {
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("The plate cannot be empty");
        }

        ParkingSpot occupiedSpot = null;
        for (ParkingSpot parkingSpot : this.parkingSpots) {
            if (parkingSpot.isOccupied() && parkingSpot.getVehicle().getVehiclePlate().equalsIgnoreCase(plate.trim())) {
                occupiedSpot = parkingSpot;
                break;
            }
        }

        if (occupiedSpot == null) {
            throw new IllegalArgumentException("Vehicle with plate " + plate + " is not currently parked");
        }

        Vehicle vehicle = occupiedSpot.getVehicle();
        int newReceiptId = this.receipts.size() + 1;
        Receipt receipt = new Receipt(newReceiptId, vehicle.getOwner(), vehicle, occupiedSpot, hours, issueDate);

        occupiedSpot.departed();
        this.receipts.add(receipt);
        return receipt;
    }

    // Removes a registered vehicle from the parking lot and from its owner's list if it is not currently parked
    public void removeVehicle(String plate) {
        Vehicle vehicle = findVehicleByPlate(plate);
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle with plate " + plate + " was not found");
        }

        for (ParkingSpot parkingSpot : this.parkingSpots) {
            if (parkingSpot.isOccupied() && parkingSpot.getVehicle().getVehiclePlate().equalsIgnoreCase(plate.trim())) {
                throw new IllegalStateException("Cannot remove a vehicle that is currently parked. Check it out first");
            }
        }

        this.vehicles.remove(vehicle);
        vehicle.getOwner().getVehicles().remove(vehicle);
    }

    // Cancels an existing receipt without removing it from the historical list
    public void cancelReceipt(int id) {
        Receipt receipt = findReceiptById(id);
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt with id " + id + " was not found");
        }
        receipt.setCancelled(true);
    }

    // Enables or disables a parking spot by its space number
    public void updateSpotStatus(int spaceNumber, boolean enabled) {
        ParkingSpot spot = findSpotByNumber(spaceNumber);
        if (spot == null) {
            throw new IllegalArgumentException("Parking spot #" + spaceNumber + " was not found");
        }
        spot.setEnabled(enabled);
    }
}
