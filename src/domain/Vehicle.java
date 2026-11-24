package domain;

import java.io.Serializable;


public abstract class Vehicle implements Serializable {
    

    private String vehiclePlate;
    private String brand;
    private Customer owner;

    public Vehicle(String newVehiclePlate, String newBrand, Customer newOwner){
        setVehiclePlate(newVehiclePlate);
        setOwner(newOwner);
        setBrand(newBrand);
    }
    
    public void setVehiclePlate(String newVehiclePlate){
        if(newVehiclePlate==null|| newVehiclePlate.isBlank()){
            throw new IllegalArgumentException("The plate of the vehicle can't be empty");
        }
        this.vehiclePlate= newVehiclePlate;
    }
    public String getVehiclePlate(){
        return vehiclePlate;
    }

    public void setBrand(String newBrand){
        if(newBrand==null|| newBrand.isBlank()){
            throw new IllegalArgumentException("The brand of the vehicle can't be empty");
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

