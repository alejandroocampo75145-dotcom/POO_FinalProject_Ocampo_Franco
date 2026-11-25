# Smart Parking System
**Proyecto Final — Programación Orientada a Objetos**

**Integrantes:**
- Alejandro Ocampo Escobar (Código: 75145)
- Joshua Franco (Código: 75283)

---

## Descripción General
**Smart Parking System** es una aplicación de consola diseñada para facilitar la administración diaria de un parqueadero con capacidad fija de 22 espacios. El sistema centraliza el registro de clientes y vehículos, organiza la ocupación de los espacios según el tipo de vehículo, calcula automáticamente el valor a cobrar por horas de estadía y conserva un historial de recibos junto con el respaldo de toda la información.

---

## ¿Qué puede hacer el sistema?
- **Gestión de clientes:** Registrar nuevos clientes, consultarlos por su identificación, actualizar sus datos de contacto y desactivarlos sin borrar su historial.
- **Registro de vehículos:** Asociar carros, motos, camiones y bicicletas a cada cliente, aplicando tarifas por hora diferentes según el tipo de vehículo.
- **Control de espacios:** Administrar los 22 espacios del parqueadero, verificar cuáles están disponibles, ocupados o deshabilitados, y asegurar que cada vehículo ocupe un espacio compatible.
- **Ingreso y salida con facturación:** Registrar la entrada de un vehículo, calcular el valor total al salir según las horas de estadía y emitir un recibo detallado.
- **Historial de recibos:** Consultar los comprobantes generados y anularlos en caso de error sin eliminar el registro.
- **Recomendación de espacios:** Sugerir un espacio disponible adecuado para el vehículo que ingresa.
- **Guardado de información y reportes:** Guardar y recuperar los datos del parqueadero entre sesiones y exportar la información en archivos CSV.

---

## Estructura y Clases del Proyecto

El proyecto está organizado en tres partes principales:
- **Lógica del parqueadero (`domain`):** Contiene las entidades del sistema y las reglas de funcionamiento.
- **Manejo de archivos (`data`):** Se encarga de guardar y cargar la información del parqueadero y generar reportes.
- **Interfaz de consola (`ui`):** Muestra los menús y permite al operador interactuar con el sistema.

Las clases principales que conforman el modelo del parqueadero son:
- **`ParkingLot`:** Es la clase central que representa el parqueadero (22 espacios) y coordina las listas de clientes, vehículos, espacios y recibos.
- **`ParkingSpot`:** Representa cada espacio individual del parqueadero, indicando su número, el tipo de vehículo que acepta y si está disponible, ocupado o deshabilitado.
- **`Customer`:** Almacena los datos personales del cliente, su estado (activo o inactivo) y los vehículos registrados a su nombre.
- **`Vehicle`:** Define los datos comunes de cualquier vehículo (placa, marca y propietario) y establece el comportamiento general para calcular tarifas.
- **`Car`, `Motorcycle`, `Truck` y `Bicycle`:** Representan cada tipo de vehículo que atiende el parqueadero, cada uno con su propia tarifa por hora.
- **`Receipt`:** Representa el comprobante generado al finalizar una estadía, guardando el cliente, el vehículo, el espacio utilizado, las horas, la fecha, el total a pagar y si fue anulado.

---
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
        +toString() : String
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
