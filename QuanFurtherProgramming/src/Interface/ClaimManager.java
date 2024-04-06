package Interface;

import Claim.Claim;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClaimManager implements ClaimProcessManager{
    FileAccess fileAccess = new FileAccess();
    FileModifier fileModifier = new FileModifier();

    @Override
    public void addClaim(String Id) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String id = generateId();
        System.out.println("-- ADDING NEW CLAIM --");

        System.out.println("Enter claim date (yyyy-MM-dd):");
        Date claimDate = new Date(scanner.nextLine());

        System.out.println("Enter insured person:");
        String insuredPerson = scanner.nextLine();

        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter exam date (dd-MM-yyyy):");
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

        fileModifier.addClaim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber);
    }


    public String generateId() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileAccess.claimFile, "rw");
        String line;
        String id = null;

        while ((line = randomAccessFile.readLine()) != null){
            String[] token= line.split(",");
            id = token[0];
        }

        if(id == null){
            return "F" + "000000000001";
        }else {
            String numberString = id.substring(1);
            int afterIdScan = Integer.parseInt(numberString);

            return String.format("F%010d", 1 + afterIdScan);
        }
    }

    @Override
    public void updateClaim(String id ) {
        fileModifier.updateClaim(id);
    }

    @Override
    public void deleteClaim(String id) {
        fileModifier.deleteClaim(id);
    }

    @Override
    public void getClaimById(String claimId) {
        fileModifier.getClaimById(claimId);
    }

    @Override
    public void getAllClaims() {
        fileModifier.getAllClaims();
    }


}
