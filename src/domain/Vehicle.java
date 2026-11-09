package domain;

import java.io.Serializable;

/**
 * Abstract base class representing a generic vehicle in the parking system.
 */
public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private String vehiclePlate;
    private String brand;
    private Customer owner;

    
}

