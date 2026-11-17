package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkingLot implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String address;
    private int totalSpaces= 22;

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Receipt> receipts;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Customer> customers;

    
    
    
    
    
    
    public void setName(String newName){
        if(newName.isBlank()|| newName==null){
            throw new IllegalArgumentException("The name can't be empty");
        }
    this.name= newName;    
    }
    public String getName(){
        return name;
    }

    public void setAdress(String newAddress){
        if(newAddress.isBlank()|| newAddress==null){
            throw new IllegalArgumentException("The name can't be empty");
        }
    this.address= newAddress;    
    }
    public String getAddress(){
        return address;
    }

    public int getTotalSpaces(){
        return totalSpaces;
    }


}
