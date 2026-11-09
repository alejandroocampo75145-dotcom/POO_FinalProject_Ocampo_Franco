# Smart Parking System
**Proyecto Final — Programación Orientada a Objetos**

**Integrantes:** 
* Alejandro Ocampo Escobar (Código: 75145)
* Joshua Franco (Código: 75283)

## Descripción General
Sistema de gestión interna para un parqueadero inteligente. Permite registrar clientes, controlar la entrada y salida de distintos tipos de vehículos (Carros, Motos, Camiones, Bicicletas), asignar espacios de parqueo dinámicamente y facturar el tiempo de estadía generando un historial inmutable de recibos. Incluye persistencia de datos y exportación a CSV.

## Instrucciones de Ejecución
1. Asegurarse de tener instalado Java 25 LTS (OpenJDK 25).
2. Compilar los archivos desde la carpeta `src`.
3. Ejecutar la clase principal ubicada en `ui.MainConsole`.

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
    }
    class ParkingSpot {
    }
    class Customer {
    }
    class Receipt {
    }
    class Vehicle {
        <<Abstract>>
    }
    class Car {
    }
    class Motorcycle {
    }
    class Truck {
    }
    class Bicycle {
    }
