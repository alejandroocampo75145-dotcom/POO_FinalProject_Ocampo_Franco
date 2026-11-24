## Diagrama de Clases del Dominio
```mermaid
classDiagram
    ParkingLot "1" *-- "22" ParkingSpot : manages
    ParkingLot "1" --> "0..*" Vehicle : registered
    ParkingLot "1" --> "0..*" Customer : clients
    ParkingLot "1" --> "0..*" Receipt : history

    Vehicle "0..1" -- "1" ParkingSpot : occupies
    Vehicle "0..*" --> "1" Customer : belongs to
    
    Receipt "0..*" --> "1" Customer : billed to
    Receipt "0..*" --> "1" Vehicle : for
    Receipt "0..*" --> "1" ParkingSpot : used

    Vehicle <|-- Car
    Vehicle <|-- Motorcycle
    Vehicle <|-- Truck
    Vehicle <|-- Bicycle

    class ParkingLot {
        -TOTAL_SPACES : int$
        -name : String
        -address : String
        -totalSpaces : int
        -vehicles : ArrayList~Vehicle~
        -receipts : ArrayList~Receipt~
        -parkingSpots : ArrayList~ParkingSpot~
        -customers : ArrayList~Customer~
        +ParkingLot(newName : String, newAddress : String)
        -initializeSpots() : void
        +getName() : String
        +setName(newName : String) : void
        +getAddress() : String
        +setAddress(newAddress : String) : void
        +getTotalSpaces() : int
        +getVehicles() : ArrayList~Vehicle~
        +getReceipts() : ArrayList~Receipt~
        +getParkingSpots() : ArrayList~ParkingSpot~
        +getCustomers() : ArrayList~Customer~
        +addCustomer(customer : Customer) : void
        +findCustomerById(id : int) : Customer
        +updateCustomerInformation(id : int, newPhoneNumber : String, newEmail : String) : void
        +deActivateCustomer(id : int) : void
        +findVehicleByPlate(plate : String) : Vehicle
        +findReceiptById(id : int) : Receipt
        +addVehicle(vehicle : Vehicle) : void
        +recommendOptimalSpot(vehicle : Vehicle) : ParkingSpot
        +findSpotByNumber(spaceNumber : int) : ParkingSpot
        +parkVehicle(plate : String, spaceNumber : int) : void
        +checkoutVehicle(plate : String, hours : int, issueDate : String) : Receipt
        +removeVehicle(plate : String) : void
        +cancelReceipt(id : int) : void
        +updateSpotStatus(spaceNumber : int, enabled : boolean) : void
    }
    class ParkingSpot {
        -spaceNumber : int
        -spotType : String
        -isEnabled : boolean
        -vehicle : Vehicle
        +ParkingSpot(spaceNumber : int, spotType : String)
        +getSpaceNumber() : int
        -setSpaceNumber(spaceNumber : int) : void
        +getSpotType() : String
        -setSpotType(spotType : String) : void
        +isEnabled() : boolean
        +setEnabled(isEnabled : boolean) : void
        +getVehicle() : Vehicle
        +setVehicle(vehicle : Vehicle) : void
        +isAvailable() : boolean
        +isOccupied() : boolean
        +departed() : Vehicle
        +toString() : String
    }
    class Customer {
        -id : int
        -name : String
        -phoneNumber : String
        -email : String
        -active : boolean
        -vehicles : ArrayList~Vehicle~
        +Customer(id : int, name : String, phoneNumber : String, email : String)
        +getId() : int
        +setId(id : int) : void
        +getName() : String
        +setName(name : String) : void
        +getPhoneNumber() : String
        +setPhoneNumber(phoneNumber : String) : void
        +getEmail() : String
        +setEmail(email : String) : void
        +isActive() : boolean
        +setActive() : void
        +setDeactivate() : void
        +getVehicles() : ArrayList~Vehicle~
        +addVehicle(vehicle : Vehicle) : void
        +toString() : String
    }
    class Receipt {
        -id : int
        -customer : Customer
        -vehicle : Vehicle
        -parkingSpot : ParkingSpot
        -hours : int
        -totalAmount : double
        -issueDate : String
        -isCancelled : boolean
        +Receipt(id : int, customer : Customer, vehicle : Vehicle, parkingSpot : ParkingSpot, hours : int, issueDate : String)
        +getId() : int
        -setId(id : int) : void
        +getCustomer() : Customer
        -setCustomer(customer : Customer) : void
        +getVehicle() : Vehicle
        -setVehicle(vehicle : Vehicle) : void
        +getParkingSpot() : ParkingSpot
        -setParkingSpot(parkingSpot : ParkingSpot) : void
        +getHours() : int
        +setHours(hours : int) : void
        +getTotalAmount() : double
        +calculateTotalAmount() : double
        +getIssueDate() : String
        -setIssueDate(issueDate : String) : void
        +isCancelled() : boolean
        +setCancelled(isCancelled : boolean) : void
        +getReceiptDetails() : String
    }
    class Vehicle {
        <<Abstract>>
        -vehiclePlate : String
        -brand : String
        -owner : Customer
        +Vehicle(newVehiclePlate : String, newBrand : String, newOwner : Customer)
        +setVehiclePlate(newVehiclePlate : String) : void
        +getVehiclePlate() : String
        +setBrand(newBrand : String) : void
        +getBrand() : String
        +setOwner(newOwner : Customer) : void
        +getOwner() : Customer
        +getHourlyRate() : double*
        +getVehicleType() : String*
    }
    class Car {
        +Car(newVehiclePlate : String, newBrand : String, newOwner : Customer)
        +getHourlyRate() : double
        +getVehicleType() : String
    }
    class Motorcycle {
        +Motorcycle(newVehiclePlate : String, newBrand : String, newOwner : Customer)
        +getHourlyRate() : double
        +getVehicleType() : String
    }
    class Truck {
        +Truck(newVehiclePlate : String, newBrand : String, newOwner : Customer)
        +getHourlyRate() : double
        +getVehicleType() : String
    }
    class Bicycle {
        +Bicycle(newBrand : String, newOwner : Customer)
        +getHourlyRate() : double
        +getVehicleType() : String
    }
```
