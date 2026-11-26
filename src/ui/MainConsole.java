package ui;

import data.*;
import domain.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// Console user interface for managing the Smart Parking System
public class MainConsole {

    private static final String DATA_FILE = "parkinglot.dat";

    // =========================================================================
    // SYSTEM STARTUP & CONSOLE INPUT HELPERS (USED IN MAIN)
    // =========================================================================

    // Loads an existing parking lot from binary file or guides first-time setup
    private static ParkingLot initializeParkingLot(Scanner scanner) {
        ParkingLot parkingLot = null;
        File file = new File(DATA_FILE);

        if (file.exists()) {
            parkingLot = ParkingLotStorage.load(DATA_FILE);
            if (parkingLot != null) {
                System.out.println("Previous parking lot data loaded successfully.");
            }
        }

        while (parkingLot == null) {
            try {
                System.out.println("=== Parking Lot First-Time Setup ===");
                String name = readString(scanner, "Enter the parking lot name: ");
                String address = readString(scanner, "Enter the parking lot address: ");

                // Automatically initializes empty lists and the 22 fixed parking spots
                parkingLot = new ParkingLot(name, address);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again.\n");
            }
        }

        System.out.println("\nParking Lot ready!");
        System.out.println("Name: " + parkingLot.getName());
        System.out.println("Address: " + parkingLot.getAddress());
        System.out.println("Total spaces: " + parkingLot.getTotalSpaces());

        return parkingLot;
    }

    // Displays the main navigation menu header and options
    private static void printMainMenu(ParkingLot parkingLot) {
        System.out.println("\n========================================");
        System.out.println("   " + parkingLot.getName().toUpperCase() + " - MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Vehicle Management");
        System.out.println("2. Parking Operations (Entry / Exit / Recommendation)");
        System.out.println("3. Receipts & Billing");
        System.out.println("4. Customers Management");
        System.out.println("5. Space Management");
        System.out.println("6. CSV Export");
        System.out.println("7. Save Data");
        System.out.println("0. Save and Exit");
    }

    // Safely reads an integer from the console without crashing on invalid text
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    // Reads a trimmed text line from the console
    private static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // =========================================================================
    // 1. VEHICLE MANAGEMENT FUNCTIONS
    // =========================================================================

    // Displays the submenu for registering, listing, searching, and removing vehicles
    private static void vehicleMenu(ParkingLot parkingLot, Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Register a new vehicle");
            System.out.println("2. List all registered vehicles");
            System.out.println("3. Search vehicle by plate");
            System.out.println("4. Remove a vehicle");
            System.out.println("0. Back to main menu");

            option = readInt(scanner, "Select an option: ");

            switch (option) {
                case 1:
                    registerVehicle(parkingLot, scanner);
                    break;
                case 2:
                    listVehicles(parkingLot);
                    break;
                case 3:
                    searchVehicleByPlate(parkingLot, scanner);
                    break;
                case 4:
                    removeVehicle(parkingLot, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);
    }

    // Registers a new Car, Motorcycle, Truck, or Bicycle linked to an active customer
    private static void registerVehicle(ParkingLot parkingLot, Scanner scanner) {
        try {
            int ownerId = readInt(scanner, "Enter the owner's customer ID: ");
            Customer owner = parkingLot.findCustomerById(ownerId);

            if (owner == null) {
                System.out.println("Customer with ID " + ownerId + " was not found. Register the customer first.");
                return;
            }
            if (!owner.isActive()) {
                System.out.println("This customer is inactive and cannot register new vehicles.");
                return;
            }

            System.out.println("Select vehicle type:");
            System.out.println("1. Car ($5000/h)");
            System.out.println("2. Motorcycle ($2500/h)");
            System.out.println("3. Truck ($8000/h)");
            System.out.println("4. Bicycle ($1500/h)");
            int type = readInt(scanner, "Vehicle type: ");

            String brand = readString(scanner, "Enter the vehicle brand: ");
            Vehicle vehicle;

            switch (type) {
                case 1:
                    String carPlate = readString(scanner, "Enter the car plate: ");
                    vehicle = new Car(carPlate, brand, owner);
                    break;
                case 2:
                    String motoPlate = readString(scanner, "Enter the motorcycle plate: ");
                    vehicle = new Motorcycle(motoPlate, brand, owner);
                    break;
                case 3:
                    String truckPlate = readString(scanner, "Enter the truck plate: ");
                    vehicle = new Truck(truckPlate, brand, owner);
                    break;
                case 4:
                    // Bicycle generates its plate automatically using the owner's ID (BIKE-<id>)
                    vehicle = new Bicycle(brand, owner);
                    System.out.println("Generated bicycle plate: " + vehicle.getVehiclePlate());
                    break;
                default:
                    System.out.println("Invalid vehicle type.");
                    return;
            }

            parkingLot.addVehicle(vehicle);
            System.out.println("Vehicle registered successfully: " + vehicle);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Prints all registered vehicles
    private static void listVehicles(ParkingLot parkingLot) {
        ArrayList<Vehicle> vehicles = parkingLot.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles are registered yet.");
            return;
        }

        System.out.println("\n--- Registered Vehicles (" + vehicles.size() + ") ---");
        for (Vehicle vehicle : vehicles) {
            System.out.println("- " + vehicle);
        }
    }

    // Finds and displays a registered vehicle by its plate
    private static void searchVehicleByPlate(ParkingLot parkingLot, Scanner scanner) {
        try {
            String plate = readString(scanner, "Enter the vehicle plate to search: ");
            Vehicle vehicle = parkingLot.findVehicleByPlate(plate);

            if (vehicle == null) {
                System.out.println("No vehicle found with plate: " + plate);
            } else {
                System.out.println("Vehicle found: " + vehicle);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Removes a vehicle from the system if it is not currently parked
    private static void removeVehicle(ParkingLot parkingLot, Scanner scanner) {
        try {
            String plate = readString(scanner, "Enter the plate of the vehicle to remove: ");
            parkingLot.removeVehicle(plate);
            System.out.println("Vehicle with plate " + plate + " was removed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================================
    // 2. PARKING OPERATIONS FUNCTIONS (ENTRY / EXIT / RECOMMENDATION)
    // =========================================================================

    // Displays the submenu for spot recommendation, vehicle check-in, and checkout
    private static void parkingOperationsMenu(ParkingLot parkingLot, Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Parking Operations ---");
            System.out.println("1. Recommend optimal spot for a vehicle");
            System.out.println("2. Park a vehicle (Check-in)");
            System.out.println("3. Checkout a vehicle & generate receipt");
            System.out.println("0. Back to main menu");

            option = readInt(scanner, "Select an option: ");

            switch (option) {
                case 1:
                    recommendSpotForVehicle(parkingLot, scanner);
                    break;
                case 2:
                    parkVehicle(parkingLot, scanner);
                    break;
                case 3:
                    checkoutVehicle(parkingLot, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);
    }

    // Suggests the first available parking spot compatible with a registered vehicle
    private static void recommendSpotForVehicle(ParkingLot parkingLot, Scanner scanner) {
        try {
            String plate = readString(scanner, "Enter the vehicle plate: ");
            Vehicle vehicle = parkingLot.findVehicleByPlate(plate);

            if (vehicle == null) {
                System.out.println("Vehicle with plate " + plate + " was not found.");
                return;
            }

            ParkingSpot recommended = parkingLot.recommendOptimalSpot(vehicle);
            if (recommended == null) {
                System.out.println("No available spots compatible with " + vehicle.getVehicleType() + ".");
            } else {
                System.out.println("Recommended spot: " + recommended);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Parks a registered vehicle after showing an automatic compatible spot recommendation
    private static void parkVehicle(ParkingLot parkingLot, Scanner scanner) {
        try {
            String plate = readString(scanner, "Enter the vehicle plate to park: ");
            Vehicle vehicle = parkingLot.findVehicleByPlate(plate);

            if (vehicle == null) {
                System.out.println("Vehicle with plate " + plate + " was not found. Register it first.");
                return;
            }

            ParkingSpot recommended = parkingLot.recommendOptimalSpot(vehicle);
            if (recommended != null) {
                System.out.println("Recommended spot: " + recommended);
            } else {
                System.out.println("Warning: No available spots found for " + vehicle.getVehicleType() + ".");
            }

            int spaceNumber = readInt(scanner, "Enter the parking spot number (0 - 21): ");
            parkingLot.parkVehicle(plate, spaceNumber);
            System.out.println("Vehicle " + plate + " parked successfully in spot #" + spaceNumber + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Checks out a parked vehicle, calculates the fee, frees the spot, and prints the receipt
    private static void checkoutVehicle(ParkingLot parkingLot, Scanner scanner) {
        try {
            String plate = readString(scanner, "Enter the vehicle plate to check out: ");
            int hours = readInt(scanner, "Enter the total hours parked: ");
            String issueDate = LocalDate.now().toString();

            Receipt receipt = parkingLot.checkoutVehicle(plate, hours, issueDate);
            System.out.println("\nCheckout completed successfully!");
            System.out.println(receipt.getReceiptDetails());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================================
    // 3. RECEIPTS & BILLING FUNCTIONS
    // =========================================================================

    // Displays the submenu for viewing, searching, and cancelling receipts
    private static void receiptMenu(ParkingLot parkingLot, Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Receipts & Billing ---");
            System.out.println("1. View all receipts");
            System.out.println("2. Search receipt by ID");
            System.out.println("3. Cancel a receipt");
            System.out.println("0. Back to main menu");

            option = readInt(scanner, "Select an option: ");

            switch (option) {
                case 1:
                    listReceipts(parkingLot);
                    break;
                case 2:
                    searchReceiptById(parkingLot, scanner);
                    break;
                case 3:
                    cancelReceipt(parkingLot, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);
    }

    // Prints the complete historical list of generated receipts
    private static void listReceipts(ParkingLot parkingLot) {
        ArrayList<Receipt> receipts = parkingLot.getReceipts();
        if (receipts.isEmpty()) {
            System.out.println("No receipts have been generated yet.");
            return;
        }

        System.out.println("\n--- Receipt History (" + receipts.size() + ") ---");
        for (Receipt receipt : receipts) {
            System.out.println("- " + receipt.getReceiptDetails());
        }
    }

    // Finds and displays a specific receipt by its ID
    private static void searchReceiptById(ParkingLot parkingLot, Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter the receipt ID: ");
            Receipt receipt = parkingLot.findReceiptById(id);

            if (receipt == null) {
                System.out.println("No receipt found with ID #" + id);
            } else {
                System.out.println(receipt.getReceiptDetails());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Cancels an active receipt without deleting it from the historical record
    private static void cancelReceipt(ParkingLot parkingLot, Scanner scanner) {
        try {
            int id = readInt(scanner, "Enter the ID of the receipt to cancel: ");
            parkingLot.cancelReceipt(id);
            System.out.println("Receipt #" + id + " was cancelled successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================================
    // 4. CUSTOMERS MANAGEMENT
    // =========================================================================

    private static void customerMenu(ParkingLot parkingLot , Scanner scanner){
        int option;

        do{
            System.out.println("1. Register a new customer");
            System.out.println("2. List all customers");
            System.out.println("3. Search customer by ID");
            System.out.println("4. Update customer information");
            System.out.println("5. Deactivate a customer");
            System.out.println("0. Back to main menu");

            option = readInt(scanner, "Select an option: ");
            switch(option){
                case 1:
                    registerCustomer(parkingLot, scanner);
                    break;
                case 2:
                    listCustomers(parkingLot, scanner);
                    break;
                case 3:
                    searchCustomerByid(parkingLot, scanner);
                    break;
                case 4: 
                    updateCustomerInformation(parkingLot, scanner);
                    break;
                case 5:
                    deActivateCustomer(parkingLot, scanner);
                    break;
                case 0:
                    System.out.println("Returning to Principal menu");
                    break;
                default:
                    System.out.println("Invalid Option please try again");
            }
        }while (option != 0);
    }

    // Register customers

    private static void registerCustomer(ParkingLot parkingLot, Scanner scanner){
        try{
        int id = readInt(scanner, "Enter customer ID: ");
        String name = readString(scanner, "Enter the customer name: ");
        String phoneNumber = readString(scanner, "Enter the customer's phone number: ");
        String email = readString(scanner, "Enter the customer's email: ");

        Customer customer = new Customer(id, name, phoneNumber, email);
        
        parkingLot.addCustomer(customer);
        System.out.println("Customer registered successfully: " + customer);
             }
        catch(IllegalArgumentException e ){
            System.out.println("Error: " + e.getMessage());

        }
    }

    // List the customers 

    private static void listCustomers(ParkingLot parkingLot, Scanner scanner){
        ArrayList<Customer> customers = parkingLot.getCustomers();
        if(customers.isEmpty()){
            System.out.println("There are not registered customers At the moment ");
        }
        System.out.println("\n--- Registered Customers (" + customers.size() + ") ---");
        for (Customer customer : customers) {
            System.out.println("- " + customer);
        }
    }

    // Search customer by id 

    private static void  searchCustomerByid(ParkingLot parkingLot, Scanner scanner){
        int id = readInt(scanner, "Enter the desired Id");
        Customer customer = parkingLot.findCustomerById(id);
        if (customer == null){
            System.out.println("No customer found with ID: " + id);
        } else {
        System.out.println("Customer found: " + customer);
        }

    }

    //update customer's information 

    private static void updateCustomerInformation(ParkingLot parkingLot, Scanner scanner){
        try{
        int id = readInt(scanner, "Enter the id to update its information");
        Customer customer = parkingLot.findCustomerById(id);
        if (customer == null){
            System.out.println("No customer found ");
            return;
        }
        System.out.println("Update information for the client: " + customer);
        String newphoneNumber = readString(scanner, "New Phone number");
        String newEmail = readString(scanner, "New Email");

        parkingLot.updateCustomerInformation(id, newphoneNumber, newEmail);
        System.out.println("Customer updated successfully: " + customer);
        }
        catch(IllegalArgumentException e){
            System.out.println("Customer information was not updated try again" + e.getMessage());
        }
    }

    // deactivate Customer

    private static void deActivateCustomer(ParkingLot parkingLot, Scanner scanner){
        try{
        int id = readInt(scanner, "Enter the Id of the client to deactivate");
        parkingLot.deActivateCustomer(id);
        System.out.println("Client with the id: " + id + " Deactivaded");

        }catch(IllegalArgumentException e){
            System.out.println("Error  " + e.getMessage());
        }    
    }



    // =========================================================================
    // 5. SPACES MANAGEMENT
    // =========================================================================

    private static void spacesManagement(ParkingLot parkingLot, Scanner scanner){
        int option;
        do { 
            System.out.println("---Sapaces Management---");
            System.out.println("1. Show all the spaces");
            System.out.println("2. Show only empty spaces");
            System.out.println("3. clear a Space");
            System.out.println("4. Disable space");
            System.out.println("0. Return to principal menu");

            option = readInt(scanner, "Select your desired option");
            
            switch(option){
                case 1:
                    showAllSpaces(parkingLot);
                    break;
                case 2:
                    ShowEmptySpaces(parkingLot);
                    break;
                case 3:
                    clearSpace(parkingLot, scanner);
                    break;
                case 4:
                    disableSpace(parkingLot, scanner);
                    break;
                case 0:
                    System.out.println("Returning to principal menu");
                    break;
                default:
                    System.out.println("Invalid option, try again");

            }

        } while (option != 0);
    }

    // show all spaces

    private static void showAllSpaces(ParkingLot parkingLot){
        ArrayList<ParkingSpot> parkingSpots = parkingLot.getParkingSpots();
        System.out.println("--- Parking Spots ---");
        for (ParkingSpot parkingSpot : parkingSpots){
            System.out.println("-" + parkingSpot);
        }
    }

    //show empty spaces

    private static void ShowEmptySpaces(ParkingLot parkingLot){
        ArrayList<ParkingSpot> parkingSpots = parkingLot.getParkingSpots();
        for (ParkingSpot parkingSpot : parkingSpots){
            if(parkingSpot.isAvailable()){
                System.out.println("- " + parkingSpot);
            }
        }
    }

    // clear/ activate a space 

    private static void clearSpace(ParkingLot parkingLot, Scanner scanner){
        
        showAllSpaces(parkingLot);
        try{
        int spaceNumber = readInt(scanner, "From the Spaces shown select the one to activate(empty the vehicle) Choose a number between 0-21");
        parkingLot.updateSpotStatus(spaceNumber, true);
        System.out.println("Spot activated succesfully");
        }catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error "+ e.getMessage());

        }

    }
    private static void disableSpace(ParkingLot parkingLot, Scanner scanner){
        showAllSpaces(parkingLot);
        try{
        int spaceNumber = readInt(scanner, "From the Spaces shown select the one to desactivate(occuped or in mainteninance) Choose a number between 0-21");
        parkingLot.updateSpotStatus(spaceNumber, false);
        System.out.println("Spot desactivated succesfully");
        }catch(IllegalArgumentException | IllegalStateException  e){
            System.out.println("Error "+ e.getMessage());

        }

    }
    
     // =========================================================================
    // CSV Exports Menu
    // =========================================================================

    private static void exportCSV(ParkingLot parkingLot, Scanner scanner){
        int option;
        do { 
            System.out.println("---CSV Export---");
            System.out.println("1. Export customers");
            System.out.println("2. Export Receipts");
            System.out.println("0. back to main menu");
            

            option = readInt(scanner, "Select your desired option");
            
            switch(option){
                case 1:
                    String fileName = readString(scanner, "Write the name of the file to be downloaded with the extension .csv");
                    if (!fileName.toLowerCase().endsWith(".csv")) {
                        fileName += ".csv";
                    }
                    try {
                         CsvExporter.exportCustomers(parkingLot.getCustomers(), fileName);
                         System.out.println("Exported successfully to " + fileName);
                        } catch (IOException e) {
                        System.out.println("Could not export customers: " + e.getMessage());
                        }
                    break;
                case 2:
                    String file = readString(scanner, "Write the name of the file to be downloaded");
                    if (!file.toLowerCase().endsWith(".csv")) {
                        file += ".csv";
                    }
                    try {
                        CsvExporter.exportReceipt(parkingLot.getReceipts(), file);
                        System.out.println("Exported successfully to " + file);
                        } catch (IOException e) {
                        System.out.println("Could not export Receipts: " + e.getMessage());
                        }
                    break;
                case 0:
                    System.out.println("Returning to main menu");
                    break;
                
                default:
                    System.out.println("Invalid option, try again");

            }

        } while (option != 0);
    }
    



    // =========================================================================
    // MAIN ENTRY POINT (ONLY STARTUP & MAIN MENU)
    // =========================================================================

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot parkingLot = initializeParkingLot(scanner);

        int option;
        do {
            printMainMenu(parkingLot);
            option = readInt(scanner, "Select an option: ");

            switch (option) {
                case 1:
                    vehicleMenu(parkingLot, scanner);
                    break;
                case 2:
                    parkingOperationsMenu(parkingLot, scanner);
                    break;
                case 3:
                    receiptMenu(parkingLot, scanner);
                    break;
                case 4:
                    customerMenu(parkingLot, scanner);
                    break;
                case 5:
                    spacesManagement(parkingLot, scanner);
                    break;
                case 6:
                    exportCSV(parkingLot, scanner);
                    break;
                case 7:
                    ParkingLotStorage.save(parkingLot, DATA_FILE);
                    break;
                case 0:
                    ParkingLotStorage.save(parkingLot, DATA_FILE);
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);

        scanner.close();
    }
}
