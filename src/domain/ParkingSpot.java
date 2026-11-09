package domain;

import java.io.Serializable;

public class ParkingSpot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int spaceNumber;
    private String spotType;
    private boolean isEnabled;
    private Vehicle vehicle;

    // Constructores, getters, setters y métodos de negocio...
}
