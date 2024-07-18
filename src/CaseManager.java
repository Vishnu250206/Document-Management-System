import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import db.DBConnection;

public class CaseManager {
    private Connection connect() {
        return DBConnection.getConnection();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Case Management");
            System.out.println("1. Create New Case");
            System.out.println("2. View Case Details");
            System.out.println("3. Update Case Information");
            System.out.println("4. Close Case");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createCase();
                    break;
                case 2:
                    viewCase();
                    break;
                case 3:
                    updateCase();
                    break;
                case 4:
                    closeCase();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createCase() {
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
        System.out.print("Enter case number: ");
        String caseNumber = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter status: ");
        String status = scanner.nextLine();
        java.sql.Date openDate = new java.sql.Date(System.currentTimeMillis());

        // Check if client exists
        String checkClientSql = "SELECT * FROM Client WHERE client_id = ?";
        try (Connection conn = connect(); PreparedStatement checkClientStmt = conn.prepareStatement(checkClientSql)) {
            checkClientStmt.setInt(1, clientId);
            ResultSet rs = checkClientStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Error: Client ID does not exist. Please add the client first.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking client: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO CaseTable (client_id, case_number, description, status, open_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            pstmt.setString(2, caseNumber);
            pstmt.setString(3, description);
            pstmt.setString(4, status);
            pstmt.setDate(5, openDate);
            pstmt.executeUpdate();
            System.out.println("Case created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating case: " + e.getMessage());
        }
    }

    private void viewCase() {
        Scanner scanner = new Scanner(System.in);
        int caseId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter case ID: ");
                caseId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid case ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        String sql = "SELECT * FROM CaseTable WHERE case_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, caseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Case ID: " + rs.getInt("case_id"));
                System.out.println("Client ID: " + rs.getInt("client_id"));
                System.out.println("Case Number: " + rs.getString("case_number"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Open Date: " + rs.getDate("open_date"));
                System.out.println("Close Date: " + rs.getDate("close_date"));
            } else {
                System.out.println("Case not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing case: " + e.getMessage());
        }
    }

    private void updateCase() {
        Scanner scanner = new Scanner(System.in);
        int caseId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter case ID: ");
                caseId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid case ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        System.out.print("Enter new case number: ");
        String caseNumber = scanner.nextLine();
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        String sql = "UPDATE CaseTable SET case_number = ?, description = ?, status = ? WHERE case_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, caseNumber);
            pstmt.setString(2, description);
            pstmt.setString(3, status);
            pstmt.setInt(4, caseId);
            pstmt.executeUpdate();
            System.out.println("Case updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating case: " + e.getMessage());
        }
    }

    private void closeCase() {
        Scanner scanner = new Scanner(System.in);
        int caseId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter case ID: ");
                caseId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid case ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        java.sql.Date closeDate = new java.sql.Date(System.currentTimeMillis());

        String sql = "UPDATE CaseTable SET status = 'Closed', close_date = ? WHERE case_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, closeDate);
            pstmt.setInt(2, caseId);
            pstmt.executeUpdate();
            System.out.println("Case closed successfully.");
        } catch (SQLException e) {
            System.out.println("Error closing case: " + e.getMessage());
        }
    }
}
