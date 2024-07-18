import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import db.DBConnection;

public class ClientManager {
    private Connection connect() {
        return DBConnection.getConnection();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Client Management");
            System.out.println("1. Add New Client");
            System.out.println("2. View Client Details");
            System.out.println("3. Update Client Information");
            System.out.println("4. Delete Client");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    viewClient();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        String sql = "INSERT INTO Client (name, contact_number, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int clientId = rs.getInt(1);
                System.out.println("Client added successfully with ID: " + clientId);
            } else {
                System.out.println("Client added successfully but failed to retrieve client ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding client: " + e.getMessage());
        }
    }

    private void viewClient() {
        Scanner scanner = new Scanner(System.in);
        int clientId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter client ID: ");
                clientId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid client ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        String sql = "SELECT * FROM Client WHERE client_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Client ID: " + rs.getInt("client_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Contact Number: " + rs.getString("contact_number"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Address: " + rs.getString("address"));
            } else {
                System.out.println("Client not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing client: " + e.getMessage());
        }
    }

    private void updateClient() {
        Scanner scanner = new Scanner(System.in);
        int clientId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter client ID: ");
                clientId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid client ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        String sql = "UPDATE Client SET name = ?, contact_number = ?, email = ?, address = ? WHERE client_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setInt(5, clientId);
            pstmt.executeUpdate();
            System.out.println("Client updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating client: " + e.getMessage());
        }
    }

    private void deleteClient() {
        Scanner scanner = new Scanner(System.in);
        int clientId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter client ID: ");
                clientId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid client ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        String sql = "DELETE FROM Client WHERE client_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            pstmt.executeUpdate();
            System.out.println("Client deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }
}
