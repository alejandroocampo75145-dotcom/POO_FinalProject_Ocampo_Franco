package domain;

import java.io.Serializable;


public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private String vehiclePlate;
    private String brand;
    private Customer owner;

    public Vehicle(String newVehiclePlate, String newBrand, Customer newOwner){
        setVehicleTape(newVehiclePlate);
        setOwner(newOwner);
        setBrand(newBrand);
    }
    
    public void setVehicleTape(String newVehiclePlate){
        if(newVehiclePlate==null|| newVehiclePlate.isBlank()){
            throw new IllegalArgumentException("The tape of the vehicle can't be empty");
        }
        this.vehiclePlate= newVehiclePlate;
    }
    public String getVehicleTape(){
        return vehiclePlate;
    }

    public void setBrand(String newBrand){
        if(newBrand.isEmpty()|| newBrand.isBlank()){
            throw new IllegalArgumentException("The tape of the vehicle can't be empty");
        }
        this.brand= newBrand;
    }
    public String getBrand(){
        return brand;
    }

    public void setOwner(Customer newOwner){
        if(newOwner==null){
            throw new IllegalArgumentException("The class of Customer can't be null");
        }
        this.owner=newOwner;
    }
    public Customer getOwner(){
        return owner;
    }

    public abstract double getHourlyRate();

    public abstract String getVehicleType();

}

