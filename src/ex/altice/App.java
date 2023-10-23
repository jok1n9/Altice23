package ex.altice;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {
        
        List<CDR> records = new ArrayList<>();
        List<UseCost> useCosts = new ArrayList<>();
        List<BillingAccount> accounts = new ArrayList<>();
        List<ChargingRequest> requests = new ArrayList<>();
        
        //fill with billingaccount.txt path
        String accountsFilePath = "../altice/populate/billingaccount.txt";
        //fill with requests.txt
        String requestsFilePath = "../altice/populate/requests.txt";

        loadAccountsFromFile(accounts, accountsFilePath);
        loadRequestsFromFile(requests, requestsFilePath);
        
        for(ChargingRequest request:requests)
        {
            for(BillingAccount account: accounts){
                if(account.getMsisdn().equals(request.getMsisdn())){
                    Processor process = new Processor(request, account);
                    process.processRequest();
                    Vizualizer.insertSortedCDR(records, process.getRequestRecord());
                    Vizualizer.insertSortedCDR(records, process.getReplyRecord());
                    if(process.getMessage().equals("OK"))Vizualizer.insertSortedUseCost(useCosts, process.getUseCost());
                    
                }
            }
        }

        Vizualizer.display(useCosts);
    
    }

    //loads billing accounts from file
    static private void loadAccountsFromFile(List<BillingAccount> accounts, String fileName){
        

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] values = line.split(",");
                if (values.length == 9) {
                    String msisdn = values[0];
                    int bucket1 = Integer.parseInt(values[1]);
                    int bucket2 = Integer.parseInt(values[2]);
                    int bucket3 = Integer.parseInt(values[3]);
                    int counterA = Integer.parseInt(values[4]);
                    int counterB = Integer.parseInt(values[5]);
                    int counterC = Integer.parseInt(values[6]);
                    String tariffServiceA = values[7];
                    String tariffServiceB = values[8];
                    accounts.add(new BillingAccount(msisdn,bucket1, bucket2, bucket3, counterA, counterB, counterC, tariffServiceA, tariffServiceB));
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void loadRequestsFromFile(List<ChargingRequest> requests, String fileName){

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] values = line.split(",");
                if (values.length == 4) {
                    String service = values[0];
                    boolean isRoaming = Boolean.parseBoolean(values[1]);
                    String msisdn = values[2];
                    int rsu = Integer.parseInt(values[3]);
                    requests.add(new ChargingRequest(service, isRoaming, msisdn, rsu));
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}