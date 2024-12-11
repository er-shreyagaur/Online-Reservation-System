# Online Reservation System

## Overview

The **Online Reservation System** is a Java-based application that allows users to book and manage train reservations. The system connects to a MySQL database to authenticate users, book reservations, cancel reservations, and display all reservations.

## Features

- **User Authentication**: Secure user login with username and password.
- **Book Reservation**: Users can book a train reservation by providing necessary details.
- **Cancel Reservation**: Users can cancel their booked reservations using their PNR number.
- **Show All Reservations**: Display all the reservations made in the system.

## Database Connection

The application connects to a MySQL database named `OnlineReservationSystem` using JDBC. The database connection details are as follows:
- **URL**: `jdbc:mysql://localhost:3306/OnlineReservationSystem`
- **Username**: `root`
- **Password**: `sihgodseye`

## Functionality

### **1. Connect to Database**

Establishes a connection to the MySQL database.

### **2. User Authentication**

Authenticates users by checking the provided username and password against the database.

### **3. Book Reservation**

Allows users to book a train reservation by providing necessary details such as user ID, train number, train name, class type, journey date, from place, to place, and PNR number.

### **4. Cancel Reservation**

Allows users to cancel an existing reservation using their PNR number.

### **5. Show All Reservations**

Displays all reservations in the system, including details like reservation ID, user ID, train number, train name, class type, journey date, from place, to place, PNR number, and status.

### **6. Main Method**

Entry point of the application where users interact with the system through a console interface.

## Dependencies

- **JDBC Driver**: MySQL Connector/J

## How to Run

1. **Setup Database**:
    - Create a MySQL database named `OnlineReservationSystem`.
    - Create the necessary tables (`Users`, `Reservations`) as per your database schema.

2. **Update Database Configuration**:
    - Modify the database URL, username, and password in the `connect()` method if needed.

3. **Compile and Run**:
    - Compile the Java code: `javac OnlineReservationSystem.java`
    - Run the application: `java OnlineReservationSystem`

## Future Enhancements

- Implement GUI for a better user experience.
- Add more functionalities like updating reservations, user registration, etc.
- Enhance security by hashing passwords and using secure connections.
