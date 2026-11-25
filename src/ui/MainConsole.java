package ui;

import data.*;
import domain.*;
import java.io.File;
import java.util.Scanner;

// Console user interface for managing the Smart Parking System
public class MainConsole {

    public static void main(String[] args) {
        String dataFile = "parkinglot.dat";
        Scanner scanner = new Scanner(System.in);

        // Reserve the variable (the ParkingLot object is NOT created yet)
        ParkingLot parkingLot = null;

        // Check if there is already a saved file from a previous session
        File file = new File(dataFile);
        if (file.exists()) {
            parkingLot = ParkingLotStorage.load(dataFile);
        }

        // If nothing was loaded, ask the user for name and address manually
        while (parkingLot == null) {
            try {
                System.out.println("=== Parking Lot Setup ===");

                System.out.print("Enter the parking lot name: ");
                String name = scanner.nextLine();

                System.out.print("Enter the parking lot address: ");
                String address = scanner.nextLine();

                // The ParkingLot is created with the user's name and address.
                // The constructor automatically creates the empty lists and the 22 fixed spots.
                parkingLot = new ParkingLot(name, address);

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again.\n");
            }
        }

        // Now that parkingLot already exists with its data, we can call its getters
        System.out.println("\nParking Lot ready!");
        System.out.println("Name: " + parkingLot.getName());
        System.out.println("Address: " + parkingLot.getAddress());
        System.out.println("Total spaces: " + parkingLot.getTotalSpaces());


    }
}
