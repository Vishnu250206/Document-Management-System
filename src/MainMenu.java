import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DocumentManager documentManager = new DocumentManager();
        ClientManager clientManager = new ClientManager();
        CaseManager caseManager = new CaseManager();

        while (true) {
            System.out.println("Document Management System");
            System.out.println("1. Manage Documents");
            System.out.println("2. Manage Clients");
            System.out.println("3. Manage Cases");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    documentManager.showMenu();
                    break;
                case 2:
                    clientManager.showMenu();
                    break;
                case 3:
                    caseManager.showMenu();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
