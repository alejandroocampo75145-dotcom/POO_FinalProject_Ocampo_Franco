package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkingLot implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String address;
    private int totalSpaces;

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Receipt> receipts;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Customer> customers;

    
}
