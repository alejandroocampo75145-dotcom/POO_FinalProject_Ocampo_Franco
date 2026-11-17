# Smart Parking System
**Proyecto Final — Programación Orientada a Objetos (Java 25 LTS)**

**Integrantes:** 
* Alejandro Ocampo Escobar (Código: 75145)
* Joshua Franco (Código: 75283)

---

## Descripción General
Sistema de gestión interna diseñado para el personal operativo y administrativo de un parqueadero inteligente. Permite administrar clientes, registrar y clasificar distintos tipos de vehículos (Carros, Motos, Camiones y Bicicletas), asignar espacios de parqueo dinámicamente, calcular tarifas mediante polimorfismo y emitir un historial inmutable de recibos. Incluye persistencia binaria mediante serialización de objetos, exportación de reportes a archivos `.csv` y un módulo de apoyo a decisiones para recomendación de espacios y análisis de ocupación.

---

## Arquitectura del Proyecto
```text
POO_FinalProject_Ocampo_Franco/
├── src/
│   ├── ui/         # Interfaz de usuario por consola (menús e interacción)
│   ├── domain/     # Entidades del modelo, reglas de negocio y lógica de decisión
│   └── data/       # Persistencia serializada (.dat/.ser) y exportación a CSV
└── README.md
```

---

## Instrucciones de Ejecución
1. Verificar que **Java 25 LTS (OpenJDK 25)** esté instalado (`java -version`).
2. Abrir una terminal en la raíz del repositorio y compilar el código fuente:
   ```bash
   javac -d bin src/domain/*.java src/data/*.java src/ui/*.java
   ```
3. Ejecutar la aplicación desde la clase principal:
   ```bash
   java -cp bin ui.MainConsole
   ```
   *(También puede ejecutarse directamente abriendo el proyecto en Visual Studio Code y corriendo `MainConsole.java`).*

---

## Diagrama de Clases del Dominio
```mermaid
classDiagram
    ParkingLot "1" *-- "0..*" ParkingSpot : manages
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
        -name : String
        -address : String
        -totalSpaces : int
        -vehicles : ArrayList~Vehicle~
        -receipts : ArrayList~Receipt~
        -parkingSpots : ArrayList~ParkingSpot~
        -customers : ArrayList~Customer~
        +ParkingLot(name : String, address : String, totalSpaces : int)
        +getName() : String
        +setName(name : String) : void
        +getAddress() : String
        +setAddress(address : String) : void
        +getTotalSpaces() : int
        +setTotalSpaces(totalSpaces : int) : void
        +getVehicles() : ArrayList~Vehicle~
        +getReceipts() : ArrayList~Receipt~
        +getParkingSpots() : ArrayList~ParkingSpot~
        +getCustomers() : ArrayList~Customer~
        +findCustomerById(id : int) : Customer
        +findVehicleByPlate(plate : String) : Vehicle
        +findSpotByNumber(spaceNumber : int) : ParkingSpot
        +findReceiptById(receiptId : int) : Receipt
        +addCustomer(customer : Customer) : void
        +addVehicle(vehicle : Vehicle) : void
        +addParkingSpot(spot : ParkingSpot) : void
        +parkVehicle(plate : String, spaceNumber : int) : void
        +checkoutVehicle(plate : String, hours : int, issueDate : String) : Receipt
        +removeVehicle(plate : String) : void
        +cancelReceipt(id : int) : void
        +deactivateCustomer(id : int) : void
        +updateSpotStatus(num : int, enabled : boolean) : void
        +updateCustomerContact(id : int, phone : String, email : String) : void
        +recommendOptimalSpot(vehicle : Vehicle) : ParkingSpot
    }
    class ParkingSpot {
        -spaceNumber : int
        -spotType : String
        -isEnabled : boolean
        -vehicle : Vehicle
        +ParkingSpot(spaceNumber : int, spotType : String)
        +getSpaceNumber() : int
        +setSpaceNumber(spaceNumber : int) : void
        +getSpotType() : String
        +setSpotType(spotType : String) : void
        +isEnabled() : boolean
        +setEnabled(isEnabled : boolean) : void
        +getVehicle() : Vehicle
        +setVehicle(vehicle : Vehicle) : void
        +isAvailable() : boolean
        +isOccupied() : boolean
        +departed() : Vehicle
    }
    class Customer {
        -id : int
        -name : String
        -phoneNumber : String
        -email : String
        -active : boolean
        -vehicle : Vehicle
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
        +setActive(active : boolean) : void
        +getVehicle() : Vehicle
        +setVehicle(vehicle : Vehicle) : void
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
        +getCustomer() : Customer
        +getVehicle() : Vehicle
        +getParkingSpot() : ParkingSpot
        +getHours() : int
        +setHours(hours : int) : void
        +getTotalAmount() : double
        +calculateTotalAmount() : double
        +getIssueDate() : String
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

