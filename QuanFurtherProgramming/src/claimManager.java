import java.io.*;
import java.util.*;

public class ClaimManager implements ClaimProcessManager {
    FileAccess fileAccess = new FileAccess();

    @Override
    public void addClaim(Claim claim) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String id = generateId();
        System.out.println("-- ADDING NEW CLAIM --");

        System.out.println("Enter claim date (yyyy-MM-dd):");
        Date claimDate = new Date(scanner.nextLine());

        System.out.println("Enter insured person:");
        String insuredPerson = scanner.nextLine();

        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter exam date (yyyy-MM-dd):");
        Date examDate = new Date(scanner.nextLine());

        System.out.println("Enter claim amount:");
        double claimAmount = Double.parseDouble(scanner.nextLine());

        // Assuming status input is one of New, Processing, or Done
        System.out.println("Enter claim status (New/Processing/Done):");
        String status = scanner.nextLine();

        System.out.println("Enter receiver bank:");
        String receiverBank = scanner.nextLine();

        System.out.println("Enter receiver name:");
        String receiverName = scanner.nextLine();

        System.out.println("Enter receiver number:");
        String receiverNumber = scanner.nextLine();

        List<String> documents = new ArrayList<>();

        System.out.println("Enter the number of documents:");

        int numDocuments = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numDocuments; i++) {
            System.out.println("Enter document name " + (i + 1) + ":");
            String documentName = scanner.nextLine();
            documents.add(documentName);
        }

        TextBaseUI textBaseUI = new TextBaseUI();
        textBaseUI.addClaim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber);

    }

    public String generateId() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileAccess.claimFile, "rw");
        String line;
        String id = null;

        while ((line = randomAccessFile.readLine()) != null) {
            String[] token = line.split(",");
            id = token[0];
        }

        if (id == null) {
            return "f" + "000000000001";
        } else {
            String numberString = id.substring(1);
            int afterIdScan = Integer.parseInt(numberString);

            return String.format("C%010d", 1 + afterIdScan);
        }
    }

    @Override
    public void updateClaim(Claim claim) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            List<String> lines = new ArrayList<>();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] token = currentLine.split(",");
                String id = token[0].trim();
                Date claimDate = new Date(Long.parseLong(token[1].trim())); // Adjust this based on how your date is stored
                // Update the claim if the id matches
                if (id.equals(claim.getId())) {
                    // Update the claim properties here
                    // For example:
                    // claim.setDate(claimDate);
                }
                lines.add(currentLine);
            }

            // Write back to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileAccess.claimFile));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClaim(String claimId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            List<String> lines = new ArrayList<>();
            String currentLine;

            // Read lines from the file and exclude the line containing the claim to delete
            while ((currentLine = reader.readLine()) != null) {
                String[] token = currentLine.split(",");
                String id = token[0].trim();
                if (!id.equals(claimId)) {
                    lines.add(currentLine);
                }
            }

            reader.close();

            // Write the updated content (excluding the deleted claim) back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileAccess.claimFile));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Claim getClaimById(String claimId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] token = currentLine.split(",");
                String id = token[0].trim();
                if (id.equals(claimId)) {
                    // Assuming the format of each line in claim.txt is: id,claimDate,insuredPerson,cardNumber,examDate,documents,claimAmount,status,receiverBank,receiverName,receiverNumber
                    // You need to parse each field accordingly to construct the Claim object
                    Date claimDate = new Date(Long.parseLong(token[1].trim())); // Adjust this based on how your date is stored
                    String insuredPerson = token[2].trim();
                    String cardNumber = token[3].trim();
                    Date examDate = new Date(Long.parseLong(token[4].trim())); // Adjust this based on how your date is stored
                    // Parse documents list
                    List<String> documents = Arrays.asList(token[5].split(";")); // Assuming documents are separated by semicolon
                    double claimAmount = Double.parseDouble(token[6].trim());
                    String status = token[7].trim();
                    String receiverBank = token[8].trim();
                    String receiverName = token[9].trim();
                    String receiverNumber = token[10].trim();
                    // Construct and return the Claim object
                    return new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Claim not found
    }

    @Override
    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] token = currentLine.split(",");
                String id = token[0].trim();
                // Assuming the format of each line in claim.txt is: id,claimDate,insuredPerson,cardNumber,examDate,documents,claimAmount,status,receiverBank,receiverName,receiverNumber
                // You need to parse each field accordingly to construct the Claim object
                Date claimDate = new Date(Long.parseLong(token[1].trim())); // Adjust this based on how your date is stored
                String insuredPerson = token[2].trim();
                String cardNumber = token[3].trim();
                Date examDate = new Date(Long.parseLong(token[4].trim())); // Adjust this based on how your date is stored
                // Parse documents list
                List<String> documents = Arrays.asList(token[5].split(";")); // Assuming documents are separated by semicolon
                double claimAmount = Double.parseDouble(token[6].trim());
                String status = token[7].trim();
                String receiverBank = token[8].trim();
                String receiverName = token[9].trim();
                String receiverNumber = token[10].trim();
                // Construct and add the Claim object to the list
                claims.add(new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return claims;
    } }


