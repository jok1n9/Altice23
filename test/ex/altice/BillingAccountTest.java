package ex.altice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Timestamp;
import org.junit.*;

public class BillingAccountTest {
        //processrequest
    @Test
    public void doChargeTest1()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        
        BillingAccount account = new BillingAccount("12345678910", 300, 20, 300, 0, 0, 0, "Alpha3", "Beta3");

        account.doCharge(timestamp, 0, 0.5, "Alpha1", false, 3);
        
        assertEquals(account.getBucket(0), 250);
        assertEquals(account.getCounterA(), 3);
        assertEquals(account.getCounterD(), timestamp.toLocalDateTime());
        
    }
    @Test
    public void doChargeTest2()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        
        BillingAccount account = new BillingAccount("12345678910", 300, 20, 300, 5, 0, 0, "Alpha3", "Beta3");

        account.doCharge(timestamp, 0, 0.005, "Alpha1", false, 3);
        
        assertEquals(account.getBucket(0),299);
        assertEquals(account.getCounterA(), 8);
        assertEquals(account.getCounterD(), timestamp.toLocalDateTime());
        
    }
    @Test
    public void doChargeTest3()
    {
        Timestamp timestamp= Timestamp.valueOf("2023-10-23 3:10:10.0");
        
        BillingAccount account = new BillingAccount("12345678910", 300, 20, 300, 0, 2, 0, "Alpha3", "Beta3");

        account.doCharge(timestamp, 0, 0.005, "Beta1", true, 5);
        
        assertEquals(account.getBucket(0),299);
        assertEquals(account.getCounterB(), 3);
        assertEquals(account.getCounterC(), 1);
        assertEquals(account.getCounterD(), timestamp.toLocalDateTime());
        
    }
}
