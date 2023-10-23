package ex.altice;

import static org.junit.Assert.assertEquals;
import java.sql.Timestamp;
import org.junit.*;

public class ProcessorTest {
    @Test
    public void EligibilityAlpha1()
    {
       
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha1", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
                assertEquals(process.getMessage(), "OK");


        
    }
    @Test
    public void EligibilityAlpha1false()
    {
       
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 105, 0, 0, "Alpha1", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "Não elegível: tarifário sem Roaming ou uso aos fins de semana");

        
    }
    @Test
    public void EligibilityAlpha2()
    {
       
        ChargingRequest request = new ChargingRequest("A", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 5, 300, 0, 0, 0, "Alpha2", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "OK");

        
    }
    @Test
    public void EligibilityAlpha2Buck()
    {
       
        ChargingRequest request = new ChargingRequest("A", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 105, 0, 0, "Alpha2", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "OK");

        
    }
    @Test
    public void EligibilityAlpha2falseRoam()
    {
       
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 105, 0, 0, "Alpha2", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "Não elegível");

        
    }
    @Test
    public void EligibilityAlpha3false()
    {
       
        ChargingRequest request = new ChargingRequest("A", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha3", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "Não elegível");

        
    }
    @Test
    public void EligibilityAlpha3()
    {
       
        ChargingRequest request = new ChargingRequest("A", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 5, 105, 0, 0, "Alpha3", "Beta1");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);

        assertEquals(process.getMessage(), "OK");

        
    }
    @Test
    public void EligibilityBeta2false()
    {
       
        ChargingRequest request = new ChargingRequest("B", true, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "Não elegível");

        
    }
    @Test
    public void EligibilityBeta2()
    {
       
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 5, 105, 0, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
                                                                        
        assertEquals(process.getMessage(), "OK");

        
    }
    @Test
    public void EligibilityBeta3false()
    {
       
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha3", "Beta3");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
        
        assertEquals(process.getMessage(), "Não elegível");

        
    }
    @Test
    public void EligibilityBeta3()
    {
       
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 5, 105, 0, 0, "Alpha3", "Beta3");

        Processor process = new Processor(request, account);
        process.verifyEligibility(request, account);
                                                                        
        assertEquals(process.getMessage(), "OK");

        
    }
    //check rating


    @Test
    public void ratingAlpha1roaming()
    {
       
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha1", "Beta3");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 1.9, 0.01);
        assertEquals(process.getBucket(), 1);
        
    }

    @Test
    public void ratingAlpha2LocalDay()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 10:10:10.0");
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha2", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.45,0.01);
        assertEquals(process.getBucket(), 1);
        
    }
    
    @Test
    public void ratingAlpha2LocalNight()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha2", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.20,0.01);
        assertEquals(process.getBucket(), 1);
        
    }

    @Test
    public void ratingAlpha3()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 10:10:10.0");
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.75,0.01);
        assertEquals(process.getBucket(), 2);
        
    }
    
    @Test
    public void ratingAlpha3week()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        ChargingRequest request = new ChargingRequest("A", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.95,0.01);
        assertEquals(process.getBucket(), 2);
        
    }

    @Test
    public void ratingBeta1()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 10:10:10.0");
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta1");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.09,0.01);
        assertEquals(process.getBucket(), 0);
        
    }
    
    @Test
    public void ratingBeta1Night()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        ChargingRequest request = new ChargingRequest("B", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 0, 0, "Alpha3", "Beta1");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.19,0.01);
        assertEquals(process.getBucket(), 2);
        
    }
    
    @Test
    public void ratingBeta2()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 10:10:10.0");
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.025,0.01);
        assertEquals(process.getBucket(), 1);
        
    }
    
    @Test
    public void ratingBeta2Night()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        ChargingRequest request = new ChargingRequest("B", false, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta2");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0,0.001);
        assertEquals(process.getBucket(), 1);
        
    }
    
    @Test
    public void ratingBeta3()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 10:10:10.0");
        ChargingRequest request = new ChargingRequest("B", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta3");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0.075,0.01);
        assertEquals(process.getBucket(), 2);
        
    }
    
    @Test
    public void ratingBeta3week()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-22 3:10:10.0");
        ChargingRequest request = new ChargingRequest("B", true, "12345678910", 5);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 200, 300, 0, 20, 0, "Alpha3", "Beta3");

        Processor process = new Processor(request, account);
        
        process.checkRating(request, account);
        

        assertEquals(process.getCost(), 0,0.001);
        assertEquals(process.getBucket(), 2);
        
    }

    //processrequest
    @Test
    public void processRequestTest()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        ChargingRequest request = new ChargingRequest("B", true, "12345678910", 1);
        request.setTimeStamp(timestamp);
        BillingAccount account = new BillingAccount("12345678910", 100, 20, 300, 0, 0, 0, "Alpha3", "Beta3");

        Processor process = new Processor(request, account);
        
        process.processRequest();

        

        assertEquals(process.getCost(), 0.095,0.001);
        assertEquals(process.getBucket(), 2);
        assertEquals(account.getBucket3(), 290);
        
    }
}
