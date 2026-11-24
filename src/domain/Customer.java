package domain;
import java.io.Serializable;
import java.util.ArrayList;


public class Customer implements Serializable{
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private boolean active;
    private ArrayList<Vehicle> vehicles;

    public Customer(int id, String name, String phoneNumber, String email){
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.vehicles = new ArrayList<>();
        this.active = true;
    }

    @Override
    public String toString() {
    return "Customer [ID=" + id + 
           ", Name=" + name + 
           ", Phone=" + phoneNumber + 
           ", Email=" + email + 
           ", Active=" + (active ? "Yes" : "No") + 
           ", Vehicles registered=" + vehicles.size() +"]" ;
}
    
    public void addVehicle(Vehicle vehicle){
        if(vehicle == null){
            throw  new IllegalArgumentException(" vehicle can't be null");
        }
        this.vehicles.add(vehicle);
    }

    public ArrayList<Vehicle> getVehicles() {
    return vehicles;
}

    public boolean isActive(){
        return active;
    }

    public void setActive(){
        this.active = true;
    }
    public void setDeactivate(){
        this.active = false;
    }

    public void setEmail(String email) {
    if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
        throw new IllegalArgumentException("Email must contain '@' and a domain extension");
    }
    this.email = email.trim();
    }

    public String getEmail(){
        return email;
    }


    public void setPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isBlank() || !phoneNumber.matches("\\d+")){
            throw new IllegalArgumentException(" Phone number can't be blank or a negative number");
        }
        this.phoneNumber= phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setName(String name){
        if( name == null || name.isBlank()){
            throw new IllegalArgumentException(" customer name can't be empty ");
        }
        this.name= name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        if(id <= 0){
            throw new IllegalArgumentException("The ID of the customer must be a positive integer");
        }
        this.id = id;
    }
    public int getId(){
        return id;
        
    }

}
