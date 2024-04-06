import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InsuranceClaimSystem {
    private FileAccess fileAccess = new FileAccess();
    private FileModifier fileModifier = new FileModifier();
    private List<Claim> claims = new ArrayList<>();

    public static void main(String[] args) {
        InsuranceClaimSystem system = new InsuranceClaimSystem();
        system.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("=== Insurance Claim System ===");
            System.out.println("1. View All Claims");
            System.out.println("2. Add a Claim");
            System.out.println("3. Delete Claim");
            System.out.println("4. Update Claim");
            System.out.println("5. Get Claim by ID");
            System.out.println("6. Get All Claims");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    viewAllClaims();
                    break;
                case 2:
                    addClaim();
                    break;
                case 3:
                    deleteClaim();
                    break;
                case 4:
                    updateClaim();
                    break;
                case 5:
                    getClaimById();
                    break;
                case 6:
                    getAllClaims();
                    break;
                case 7:

                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 7);
    }

    private void viewAllClaims() {
        fileModifier.getAllClaims();
    }

    private void addClaim() throws IOException {
        fileModifier.addClaim();
        fileModifier.getClaimList();
    }

    private void deleteClaim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the claim you want to delete: ");
        String id = scanner.nextLine();
        fileModifier.deleteClaim(id);
        fileModifier.getClaimList();
    }

    private void updateClaim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the claim you want to update: ");
        String id = scanner.nextLine();
        fileModifier.updateClaim(id);
        fileModifier.getClaimList();
    }

    private void getClaimById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the claim you want to retrieve: ");
        String id = scanner.nextLine();
        fileModifier.getClaimById(id);
    }

    private void getAllClaims() {
        fileModifier.getAllClaims();
    }
}
