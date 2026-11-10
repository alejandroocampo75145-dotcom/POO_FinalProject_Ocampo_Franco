package domain;
import java.io.Serializable;

public class Truck extends Vehicle implements Serializable{
    public Truck(String newVehiclePlate, String newBrand, Customer newOWner){
        super(newVehiclePlate, newBrand, newOWner);
    }

    @Override 
    public double getHourlyRate(){
        return 8000.00;
    }
    @Override
    public String getVehicleType(){
        return "TRUCK";
    }
}
