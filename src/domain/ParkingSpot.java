package domain;

import java.io.Serializable;

public class ParkingSpot implements Serializable {
    
    
    private int spaceNumber;
    private String spotType;
    private boolean isEnabled;
    private Vehicle vehicle;

    public ParkingSpot(int spaceNumber, String spotType){
        setSpaceNumber(spaceNumber);
        setSpotType(spotType);
        this.isEnabled = true;
        this.vehicle = null;

    }

    public void setEnabled(boolean isEnabled) {
    if (!isEnabled && isOccupied()) {
        throw new IllegalStateException("Cannot disable an occupied spot");
    }
    this.isEnabled = isEnabled;
}

    public boolean isEnabled(){
        return isEnabled;
    }


    public boolean isOccupied(){
        return this.vehicle != null;

    }

    public boolean isAvailable(){
        return this.isEnabled && !isOccupied();
    }

    public Vehicle departed(){
        if (!isOccupied() ){
            throw new IllegalArgumentException("This spot is already empty");
        }
        Vehicle departingVehicle = this.vehicle;
        this.vehicle = null; 
        return departingVehicle;

    }

    public void setVehicle(Vehicle vehicle){
        if(vehicle == null){
            throw new IllegalArgumentException(" the vehicle cant be null");
        }
        if(!isAvailable()){
            throw new IllegalArgumentException(" This parking Spot isn't available");
        }
        if (!vehicle.getVehicleType().equalsIgnoreCase(this.spotType)) {
        throw new IllegalArgumentException("Vehicle type (" + vehicle.getVehicleType() + ") does not match spot type (" + this.spotType + ")");
        }
        this.vehicle= vehicle;
    }
    public Vehicle getVehicle(){
        return this.vehicle;
    }


   private void setSpaceNumber(int spaceNumber) {
    if (spaceNumber < 0 || spaceNumber > 21) {
        throw new IllegalArgumentException("Space number must be between 0 and 21");
    }
    this.spaceNumber = spaceNumber;
    }

    public int getSpaceNumber(){
        return spaceNumber;
    }

    private void setSpotType(String spotType) {
    if (spotType == null || spotType.isBlank()) {
        throw new IllegalArgumentException("Spot type can't be empty");
    }
    String type = spotType.trim();
    if (!type.equalsIgnoreCase("Car") && 
        !type.equalsIgnoreCase("Motorcycle") && 
        !type.equalsIgnoreCase("Bicycle") && 
        !type.equalsIgnoreCase("Truck")) {
        throw new IllegalArgumentException("Invalid spot type. Allowed: Car, Motorcycle, Bicycle, Truck");
    }
    this.spotType = type;
    }
    public String getSpotType(){
        return spotType;
    }

    @Override 
    public String toString() {  
        String status = !isEnabled ? "Disabled" : (isOccupied() ? "Occupied by " + vehicle.getVehiclePlate() : "Available");
        return "Spot #" + spaceNumber + " [" + spotType + "] - " + status;
    }

}


