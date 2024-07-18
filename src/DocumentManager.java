import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import db.DBConnection;

public class DocumentManager {
    private Connection connect() {
        return DBConnection.getConnection();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Document Management");
            System.out.println("1. Upload New Document");
            System.out.println("2. View Document Details");
            System.out.println("3. Update Document Information");
            System.out.println("4. Delete Document");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    uploadDocument();
                    break;
                case 2:
                    viewDocument();
                    break;
                case 3:
                    updateDocument();
                    break;
                case 4:
                    deleteDocument();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void uploadDocument() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();
        java.sql.Date uploadDate = new java.sql.Date(System.currentTimeMillis());

        String sql = "INSERT INTO Document (title, description, file_path, upload_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, filePath);
            pstmt.setDate(4, uploadDate);
            pstmt.executeUpdate();
            System.out.println("Document uploaded successfully.");
        } catch (SQLException e) {
            System.out.println("Error uploading document: " + e.getMessage());
        }
    }

    private void viewDocument() {
        Scanner scanner = new Scanner(System.in);
        int documentId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter document ID: ");
                documentId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid document ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        String sql = "SELECT * FROM Document WHERE document_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Document ID: " + rs.getInt("document_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("File Path: " + rs.getString("file_path"));
                System.out.println("Upload Date: " + rs.getDate("upload_date"));
            } else {
                System.out.println("Document not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing document: " + e.getMessage());
        }
    }

    private void updateDocument() {
        Scanner scanner = new Scanner(System.in);
        int documentId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter document ID: ");
                documentId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid document ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new file path: ");
        String filePath = scanner.nextLine();

        String sql = "UPDATE Document SET title = ?, description = ?, file_path = ? WHERE document_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, filePath);
            pstmt.setInt(4, documentId);
            pstmt.executeUpdate();
            System.out.println("Document updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating document: " + e.getMessage());
        }
    }

    private void deleteDocument() {
        Scanner scanner = new Scanner(System.in);
        int documentId = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter document ID: ");
                documentId = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid document ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        String sql = "DELETE FROM Document WHERE document_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            pstmt.executeUpdate();
            System.out.println("Document deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting document: " + e.getMessage());
        }
    }
}
