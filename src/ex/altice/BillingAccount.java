package ex.altice;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BillingAccount {
    private String msisdn;
    private int[] bucket;
    private int[] counter;
    private LocalDateTime counterD;
    private String tariffServiceA;
    private String tariffServiceB;
    
    
    public BillingAccount(String msisdn, int bucket1, int bucket2, int bucket3, int counterA, int counterB,
            int counterC, String tariffServiceA, String tariffServiceB) {
        
        assert(msisdn.matches("^[1-9][0-9]{10}$"));
        assert(tariffServiceA.equals("Alpha1") || tariffServiceA.equals("Alpha2") || tariffServiceA.equals("Alpha3") ); 
        assert(tariffServiceB.equals("Beta1") || tariffServiceB.equals("Beta2") || tariffServiceB.equals("Beta3") );
        assert(bucket1>=0);
        assert(bucket2>=0);
        assert(bucket3>=0);
        

        this.msisdn = msisdn;
        this.bucket = new int[3];
        this.bucket[0] = bucket1;
        this.bucket[1] = bucket2;
        this.bucket[2] = bucket3;
        this.counter = new int[3];
        this.counter[0] = counterA;
        this.counter[1] = counterB;
        this.counter[2] = counterC;
        
        this.tariffServiceA = tariffServiceA;
        this.tariffServiceB = tariffServiceB;
    }

    public BillingAccount(String msisdn, int[] bucket, int counterA, int counterB,
            int counterC, String tariffServiceA, String tariffServiceB) {
        this.msisdn = msisdn;
        this.bucket = new int[3];
        this.bucket = bucket;
        this.counter = new int[3];
        this.counter[0] = counterA;
        this.counter[1] = counterB;
        this.counter[2] = counterC;
        
        this.tariffServiceA = tariffServiceA;
        this.tariffServiceB = tariffServiceB;
    }


    public String getMsisdn() {
        return msisdn;
    }


    public int getBucket(int num) {
        return bucket[num];
    }


    public int[] getBuckets() {
        return bucket;
    }


    public int getBucket1() {
        return bucket[0];
    }


    public int getBucket2() {
        return bucket[1];
    }


    public int getBucket3() {
        return bucket[2];
    }


    public int getCounterA() {
        return counter[0];
    }


    public int getCounterB() {
        return counter[1];
    }


    public int getCounterC() {
        return counter[2];
    }

    
    public int[] getCounters() {
        return counter;
    }


    public LocalDateTime getCounterD() {
        return counterD;
    }


    public String getTariffServiceA() {
        return tariffServiceA;
    }


    public String getTariffServiceB() {
        return tariffServiceB;
    }

    //charges and changes counting
    public void doCharge(Timestamp time, int bucket, double price, String service, boolean roaming, int gsu) {
        this.bucket[bucket] -= price*100;
        
        if(service.equals("Alpha1") || service.equals("ALpha2") || service.equals("Alpha3")) this.counter[0] += gsu;
        
        if(roaming) this.counter[2]++;
        
        if(service.equals("Beta1")) this.counter[1]++;

        this.counterD = time.toLocalDateTime();
    }

}