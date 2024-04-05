package Interface;

import Claim.Claim;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        FileModifier fileModifier = new FileModifier();
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
    public void updateClaim(Claim claim) {
        BufferedReader reader;
        BufferedWriter writer;

        String id, insuredPerson, cardNumber;
        try {
            File inputFile = new File(fileAccess.claimFile);
            File tempFile = new File("QuanFurtherProgramming/src/Data/temp.txt");

            reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            System.out.println("Please enter the ID of the claim you want to update: ");
            Scanner scanner = new Scanner(System.in);
            String idCheck = scanner.next();

            while((currentLine = reader.readLine()) != null){
                String[] token = currentLine.split(",");

                id = token[0].trim();
                String date = token[1].trim();
                insuredPerson = token[2].trim();
                cardNumber = token[3].trim();
                String examDateString = token[4].trim();
                List<String> documents = new ArrayList<>();
                for (int i = 5; i < token.length - 6; i++) {
                    documents.add(token[i].trim());
                }

                // Parsing claim amount
                double claimAmount = Double.parseDouble(token[token.length - 6].trim());

                // Parsing status
                String status = token[token.length - 5].trim();

                // Parsing receiver bank
                String receiverBank = token[token.length - 4].trim();

                // Parsing receiver name
                String receiverName = token[token.length - 3].trim();

                // Parsing receiver number
                String receiverNumber = token[token.length - 2].trim();


                if(idCheck.equals(id)){
                    System.out.println("--UPDATE CLAIM INFORMATION--");

                    System.out.println("Enter new claim's insured person: ");
                    String newInsuredPerson = scanner.next();

                    System.out.println("Enter new claim's card number: '");
                    String newCardNumber = scanner.next();

                    String insuredPersonReplace = insuredPerson.replace(insuredPerson, newInsuredPerson);
                    String newCardNumberReplace = cardNumber.replace(cardNumber, newCardNumber);

                    writer.write(id + "," + date + "," + insuredPersonReplace + "," + newCardNumberReplace + "," + examDateString + ",");
                    for (String document : documents) {
                        writer.write(document + ";");
                    }
                    writer.write("," + claimAmount + "," + status + "," + receiverBank + "," + receiverName + "," + receiverNumber);
                }else{
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if(inputFile.delete()){
                tempFile.renameTo(inputFile);
            }

        } catch (IOException e) {
             e.printStackTrace();
        }
    }


    @Override
    public void deleteClaim() {
        //Delete claim in the txt file
        String fileName = fileAccess.claimFile;

        System.out.println("Enter the ID of the claim you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        String IDRemove = scanner.next();

        try{
            File inputFile = new File(fileName);
            File tempFile = new File("QuanFurtherProgramming/src/Data/temp1.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null){
                // Check if the current line matches the line to delete
//                if(currentLine.equals(lineToRemove)){
//                    continue;
//                }

                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            if(inputFile.delete()){
                tempFile.renameTo(inputFile);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public Claim getClaimById(String claimId) {
        return null;
    }

    @Override
    public List<Claim> getAllClaims() {
        return null;
    }

    public static void main(String[] args) throws IOException, ParseException {

    }
}
