package domain;
import java.io.Serializable;

public class Motorcycle extends Vehicle implements Serializable{
    public Motorcycle(String newVehiclePlate, String newBrand, Customer newOwner){
        super(newVehiclePlate, newBrand, newOwner);
    }

    @Override 
    public double getHourlyRate(){
        return 2500.00;
    }
    @Override
    public String getVehicleType(){
        return "MOTORCYCLE";
    }
}
