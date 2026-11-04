# Hotel Reservation System

A simple console-based **Hotel Reservation System** developed using Java, JDBC, and MySQL. This project helps manage hotel reservations and store booking details in a MySQL database.

## Features

- **Reserve a Room:** Add a new hotel reservation.
- **View Reservations:** Display reservation details.
- **Get Room Number:** Find a room number using the guest's name.
- **Update Reservation:** Modify existing reservation details.
- **Delete Reservation:** Remove a reservation from the database.
- **Exit:** Close the application.

## Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- SQL

## Database Setup

1. Install MySQL and create a database:

   ```sql
   CREATE DATABASE hotel_db;
   ```

2. Create a `reservations` table with the following columns:

   - `reservation_id`
   - `guest_name`
   - `room_number`
   - `contact_number`
   - `reservation_date`

   Make sure the column names and data types match your Java code.

3. Add the MySQL Connector/J library to your project's classpath.

4. Configure your database username and password using environment variables, rather than committing credentials to GitHub.

## How to Run

1. Clone or download this repository.
2. Open the project in your Java IDE.
3. Configure the MySQL database connection.
4. Ensure the MySQL server is running and the database table is created.
5. Run `HotelReservationSystem.java`.
6. Follow the menu displayed in the console.

## Project Structure

```text
Hotel-Reservation-System/
└── Hotel_Management_System/
    └── HotelReservationSystem.java
```

*The folder structure may vary depending on how the project is organized.*

## Learning Outcomes

- Understanding Java and JDBC integration.
- Performing CRUD operations using SQL.
- Connecting a Java application to a MySQL database.
- Practicing console-based application development.

## Future Improvements

- Add room availability checking.
- Validate guest contact details.
- Improve SQL query safety using `PreparedStatement`.
- Build a graphical user interface.
- Add guest check-in and check-out functionality.

## Author

Shradha Gupta

