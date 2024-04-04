package Interface;

import Claim.Claim;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ClaimManager implements ClaimProcessManager{
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

        while ((line = randomAccessFile.readLine()) != null){
            String[] token= line.split(",");
            id = token[0];
        }

        if(id == null){
            return "f" + "000000000001";
        }else {
            String numberString = id.substring(1);
            int afterIdScan = Integer.parseInt(numberString);

            return String.format("C%010d", 1 + afterIdScan);
        }
    }
    @Override
    public void updateClaim(Claim claim) {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            writer = new BufferedWriter(new FileWriter(fileAccess.claimFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null){
                String[] token = currentLine.split(",");
                String id = token[0].trim();
                Date claimDate = ;

            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }

    @Override
    public void deleteClaim(String claimId) {

    }

    @Override
    public Claim getClaimById(String claimId) {
        return null;
    }

    @Override
    public List<Claim> getAllClaims() {
        return null;
    }

}
