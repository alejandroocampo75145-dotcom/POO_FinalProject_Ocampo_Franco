package domain;
import java.io.Serializable;

public class Bicycle extends Vehicle implements Serializable{
    public Bicycle(String newBrand, Customer newOwner){
        super("BIKE-"+ newOwner.getId(),newBrand, newOwner);
    }
    @Override
    public double getHourlyRate(){
        return 1500.0;
    }
    @Override
    public String getVehicleType(){
        return "BIKE";
    }

    
}
