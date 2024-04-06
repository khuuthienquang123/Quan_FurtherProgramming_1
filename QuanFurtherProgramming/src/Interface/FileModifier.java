package Interface; /**
 * @author <Nguyen Minh Quan - s3975128>
 */

import Claim.Claim;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileModifier {
    FileAccess fileAccess = new FileAccess();

    public void addClaim(String id, Date claimDate, String insuredPerson, String cardNumber,Date examDate, List<String> documents, double claimAmount, String status,
                         String receiverBank, String receiverName, String receiverNumber){
        try{
            RandomAccessFile raf = new RandomAccessFile(fileAccess.claimFile, "rw");
            raf.seek(raf.length());

            Claim claim = new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber);

            String data = claim.getId() + "," + claim + "," + claim.getInsuredPerson() + "," + claim.getCardNumber() + "," + claim.getDocuments() + "," + claim.getClaimAmount()
                    + claim.getStatus() + "," + claim.getReceiverBank() + "," + claim.getReceiverName() + "," + claim.getReceiverNumber();

            raf.writeBytes(data+System.getProperty("line.separator"));

            raf.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateClaim(String idCheck){
        BufferedReader reader;
        BufferedWriter writer;

        Scanner scanner = new Scanner(System.in);

        String insuredPerson, cardNumber;
        try {
            File inputFile = new File(fileAccess.claimFile);
            File tempFile = new File("QuanFurtherProgramming/src/Data/temp.txt");

            reader = new BufferedReader(new FileReader(fileAccess.claimFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null){
                String[] token = currentLine.split(",");

                String id = token[0].trim();
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

    List<Claim> claimList = new ArrayList<Claim>();
    public void getClaimList() {
        String fileName = fileAccess.claimFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String currentLine;

            if ((currentLine = reader.readLine()) != null) {
                String[] token = currentLine.split(",");

                String id = token[0].trim();

                String claimDateStr = token[1].trim();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                Date claimDate = formatter1.parse(claimDateStr);

                String insuredPerson = token[2].trim();
                String cardNumber = token[3].trim();

                String examDateStr = token[4].trim();
                Date examDate = formatter1.parse(examDateStr);

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
                claimList.add(new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBank, receiverName, receiverNumber));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClaim(String id){
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
            String lineToRemove = null;

            for(Claim check : claimList){
                if(check.getId().equals(IDRemove)){
                    lineToRemove = check.getId() + "," +
                            check.getClaimDate() + "," +
                            check.getInsuredPerson() + "," +
                            check.getCardNumber() + "," +
                            check.getExamDate() + "," +
                            String.join(",", check.getDocuments()) + "," +
                            check.getClaimAmount() + "," +
                            check.getStatus() + "," +
                            check.getReceiverBank() + "," +
                            check.getReceiverName() + "," +
                            check.getReceiverNumber();
                }
            }


            while ((currentLine = reader.readLine()) != null){
                // Check if the current line matches the line to delete
                if (currentLine.equals(lineToRemove)){
                    continue;
                }

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

    public void getClaimById(String id){
        getClaimList();

        for(Claim claim : claimList){
            if(claim.getId().equals(id)){
                System.out.println(claim);
            }
        }
    }

    public void getAllClaims(){
        getClaimList();

        for(Claim claim : claimList){
            System.out.println(claim);
            System.out.println("--------------------------------");
        }
    }
}
