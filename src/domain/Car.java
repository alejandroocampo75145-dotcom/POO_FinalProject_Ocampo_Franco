package domain;
import java.io.Serializable;

public class Car extends Vehicle implements Serializable{
    public Car(String newVehiclePlate, String newBrand, Customer newOwner){
        super(newVehiclePlate, newBrand, newOwner);
    }

    @Override 
    public double getHourlyRate(){
        return 5000.00;
    }
    @Override
    public String getVehicleType(){
        return "CAR";
    }
}
