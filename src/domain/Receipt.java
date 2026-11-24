package domain;

import java.io.Serializable;

public class Receipt implements Serializable {
    

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private int hours;
    private double totalAmount;
    private String issueDate;
    private boolean isCancelled;


    public void setId(int newID){
        if (newID<=0){
            throw new IllegalArgumentException("The id must be a possitive number");
        }

    }
}
