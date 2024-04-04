package Interface; /**
 * @author <Nguyen Minh Quan - s3975128>
 */

import Claim.Claim;
import Customer.Customers;
import InsuranceCard.InsuranceCard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TextBaseUI{
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
}
