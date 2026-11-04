package Hotel_Management_System;


import java.sql.*;
import java.util.Comparator;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "shradha";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//drivers load kiya
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {//DriverManager class k andar ek method hota hai getConnection jo 3 input leta hai url,password,username ,or in tino arguments k help se wo database k sath connect krta h.
            //Connection nam ka ek interface hota hai .jiska instance bnate h connection jo ki hold krta hai us connection ko database k sath create kiya h

            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1.Reserve a room");
                System.out.println("2.View Reservation");
                System.out.println("3.Get Room Number");
                System.out.println("4.Update Reservation");
                System.out.println("5.Delete Reservation");
                System.out.println("0.Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNo(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice.Try again!!");

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    private static void reserveRoom(Connection connection, Scanner scanner) {
        System.out.println("Enter guest name: ");
        String guestName = scanner.nextLine();
        System.out.println("Enter room number: ");
        int roomNo = scanner.nextInt();
        System.out.println("Enter contact number");
        String contactNo = scanner.next();
        scanner.nextLine();//buffer clear

        String sql = "INSERT INTO reservations(guest_name,room_number,contact_number) "
                + "VALUES('" + guestName + "' , " + roomNo + ", '" + contactNo + "')";
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation successful");
            } else {
                System.out.println("Reservation Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void viewReservation(Connection connection) {
        String sql = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations";//without condintion
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Current Reservations: ");
            System.out.println("+----------------+---------------+---------------+----------------------+---------------------+");
            System.out.println("| Reservation ID | Guest         | Room Number   | Contact Number       | Reservation Date    |");
            System.out.println("+----------------+---------------+---------------+----------------------+---------------------+");
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNo = resultSet.getInt("room_number");
                String contactNo = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                //formate and display the reservation data in table like formate
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s |\n", reservationId, guestName, roomNo, contactNo, reservationDate);
            }
            System.out.println("+----------------+---------------+---------------+----------------------+---------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getRoomNo(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter reservation ID");
        int reservationId = scanner.nextInt();
        System.out.println("Enter Guest Name");
        String guestName = scanner.next();

        String sql = "SELECT room_number FROM reservations " +
                " WHERE reservation_id = " + reservationId +
                " AND guest_name = '" + guestName + "'";//with condition

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room number for Reservation ID " + reservationId + "and Guest " + guestName + " is:" + roomNumber);

            } else {
                System.out.println("Reservation not found for given ID and guest name.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        System.out.println("Enter reservation ID to update");
        int reservationId = scanner.nextInt();
        scanner.nextLine();//buffer me jo \n bacha hai → usko remove kar deta hai
        if (!reservationExists(connection, reservationId)) {
            System.out.println("Reservation not found for the given ID");
            return;
        }
        System.out.println("Enter new guest name");
        String newGuestName = scanner.next();
        System.out.println("Enter new room number");
        int newRoomNumber = scanner.nextInt();
        System.out.println("Enter new contact number");
        String newContactNumber = scanner.next();

        String sql = "UPDATE reservations SET guest_name='" + newGuestName + "'," +
                "room_number= " + newRoomNumber + "," +
                "contact_number=' " + newContactNumber + " ' " +
                "WHERE reservation_id=" + reservationId;

        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Reservation updated succesfully");
            } else {
                System.out.println("Reservation update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation ID");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for given ID.");
                return;
            }
            String sql = "DELETE FROM reservations WHERE reservation_id= " + reservationId;
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation Deleted Successfully!");
                } else {
                    System.out.println("Reservation Deletion Failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id =" + reservationId;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservation System!!");
    }
}

