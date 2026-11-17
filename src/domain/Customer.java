package domain;
import java.io.Serializable;

public class Customer implements Serializable{
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private boolean active;
    private Vehicle vehicle;

    public Customer(int id, String name, String phoneNumber, String email, boolean active, Vehicle vehicle ){



    }
    public void setId(int id){
        if(id <= 0){
            throw new IllegalArgumentException("The tape of the vehicle can't be empty");
        }
        this.id = id;
    }
    public int getId(){
        return this.id = id;
        
    }

}
