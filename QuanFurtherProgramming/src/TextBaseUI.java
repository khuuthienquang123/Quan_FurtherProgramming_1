import Interface.ClaimManager;
import java.io.IOException;
import java.util.Scanner;

public class TextBaseUI {
    private final ClaimManager system = new ClaimManager();

    Scanner scanner = new Scanner(System.in);

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("=== Insurance Claim System ===");
            System.out.println("1. View All Claims");
            System.out.println("2. Add a Claim");
            System.out.println("3. Delete Claim");
            System.out.println("4. Update Claim");
            System.out.println("5. Get Claim by ID");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    system.getAllClaims();
                    break;
                case 2:
                    system.addClaim();
                    break;
                case 3:
                    System.out.println("-- DELETING CLAIM --");
                    System.out.println("- PLease enter the ID you want to delete: ");
                    String idDelete = scanner.next();
                    system.deleteClaim(idDelete);
                    break;
                case 4:
                    System.out.println("-- UPDATING CLAIM --");
                    System.out.println("- PLease enter the ID you want to update: ");
                    String idUpdate = scanner.next();
                    system.updateClaim(idUpdate);
                    break;
                case 5:
                    System.out.println("-- GETTING CLAIM --");
                    System.out.println("- PLease enter the claim ID you want to get: ");
                    String idClaim = scanner.next();
                    system.getClaimById(idClaim);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 7);
    }
}