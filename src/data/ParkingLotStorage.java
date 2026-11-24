package data;
import domain.ParkingLot;
import java.io.*;
public class ParkingLotStorage {
    
    public static void save(ParkingLot parkingLot, String filename){
        try(ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(parkingLot);
            System.out.println("Parking lot data saved successfully.;");
        }catch(IOException e){
            System.out.println("Error saving Parking lot data: "+ e.getMessage());
        }
    }

    public static ParkingLot load (String filename){
        try(ObjectInputStream in= new ObjectInputStream(new FileInputStream(filename))){
            return (ParkingLot) in.readObject();    
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error loading the Parking lot data: "+ e.getMessage());
            return null;
        }
    }
}
