package domain;

import java.io.Serializable;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private int hours;
    private double totalAmount;
    private String issueDate;
    private boolean isCancelled;

}
