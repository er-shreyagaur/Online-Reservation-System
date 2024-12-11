import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OnlineReservationSystem {

    private static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:1234/OnlineReservationSystem";
        String user = "root";
        String password = "11223344";

        Connection connection = DriverManager.getConnection(url, user, password);

        if (connection != null && !connection.isClosed()) {
            System.out.println("Welcome, You are now Connected to MySQL database!");
        }

        return connection;
    }

    public static boolean authenticate(String username, String password) {
        boolean isAuthenticated = false;
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isAuthenticated = true;
                System.out.println("User authenticated successfully!");
            } else {
                System.out.println("No matching user found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public static void bookReservation(int userId, String trainNumber, String trainName, String classType, String journeyDate, String fromPlace, String toPlace, String pnrNumber) {
        String query = "INSERT INTO Reservations (user_id, train_number, train_name, class_type, journey_date, from_place, to_place, pnr_number, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Booked')";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setString(2, trainNumber);
            statement.setString(3, trainName);
            statement.setString(4, classType);
            statement.setString(5, journeyDate);
            statement.setString(6, fromPlace);
            statement.setString(7, toPlace);
            statement.setString(8, pnrNumber);
            statement.executeUpdate();

            System.out.println("Reservation booked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cancelReservation(String pnrNumber) {
        String query = "UPDATE Reservations SET status = 'Cancelled' WHERE pnr_number = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, pnrNumber);
            statement.executeUpdate();

            System.out.println("Reservation cancelled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllReservations() {
        String query = "SELECT * FROM Reservations";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("All Reservations:");
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int userId = resultSet.getInt("user_id");
                String trainNumber = resultSet.getString("train_number");
                String trainName = resultSet.getString("train_name");
                String classType = resultSet.getString("class_type");
                String journeyDate = resultSet.getString("journey_date");
                String fromPlace = resultSet.getString("from_place");
                String toPlace = resultSet.getString("to_place");
                String pnrNumber = resultSet.getString("pnr_number");
                String status = resultSet.getString("status");

                System.out.printf("Reservation ID: %d, User ID: %d, Train Number: %s, Train Name: %s, Class: %s, Date: %s, From: %s, To: %s, PNR: %s, Status: %s%n",
                        reservationId, userId, trainNumber, trainName, classType, journeyDate, fromPlace, toPlace, pnrNumber, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        // Authenticate user
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (authenticate(username, password)) {
            System.out.println("Login successful!");

            while (run) {
                System.out.println("\nMAKE YOUR PREFERENCE:");
                System.out.println("1. Book Reservation");
                System.out.println("2. Cancel Reservation");
                System.out.println("3. Show All Reservations");
                System.out.println("4. Exit");

                System.out.print("Enter your Choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Book a reservation
                        System.out.print("Enter user ID: ");
                        int userId = sc.nextInt();
                        sc.nextLine(); // Consume newline

                        System.out.print("Enter train number: ");
                        String trainNumber = sc.nextLine();

                        System.out.print("Enter train name: ");
                        String trainName = sc.nextLine();

                        System.out.print("Enter class type: ");
                        String classType = sc.nextLine();

                        System.out.print("Enter journey date (YYYY-MM-DD HH:MM:SS): ");
                        String journeyDate = sc.nextLine();

                        System.out.print("Enter from place: ");
                        String fromPlace = sc.nextLine();

                        System.out.print("Enter to place: ");
                        String toPlace = sc.nextLine();

                        System.out.print("Enter PNR number: ");
                        String pnrNumber = sc.nextLine();

                        bookReservation(userId, trainNumber, trainName, classType, journeyDate, fromPlace, toPlace, pnrNumber);
                        break;

                    case 2:
                        // Cancel a reservation
                        System.out.print("Enter PNR number to cancel: ");
                        String cancelPnrNumber = sc.nextLine();
                        cancelReservation(cancelPnrNumber);
                        break;

                    case 3:
                        // Show all reservations
                        showAllReservations();
                        break;

                    case 4:
                        // Exit
                        System.out.println("Thank You, Have A Nice Day...");
                        run = false;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid credentials.");
        }

        sc.close();
    }
}
