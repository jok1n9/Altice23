package ex.altice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;


public class CDR {
    private Timestamp timeStamp;
    private String msisdn;
    private String serviceId;
    private ChargingRequest chargingRequest;
    private ChargingReply chargingReply;
    private int[] bucket;
    private int[] counter;
    private LocalDateTime counterD;
    private String appliedTariff;
    
    
    public CDR(Timestamp timeStamp, String msisdn, String serviceId, 
            ChargingReply chargingReply, int[] bucket, int[] counter, LocalDateTime counterD, String appliedTariff) {
        
        
        this.timeStamp = timeStamp;
        this.msisdn = msisdn;
        this.serviceId = serviceId;
        this.chargingReply = chargingReply;
        this.bucket = new int[3];
        this.bucket[0] = bucket[0];
        this.bucket[1] = bucket[1];
        this.bucket[2] = bucket[2];
        this.counter = new int[3];
        this.counter[0] = counter[0];
        this.counter[1] = counter[1];
        this.counter[2] = counter[2];
        this.counterD = counterD;
        this.appliedTariff = appliedTariff;

    }
    public CDR(Timestamp timeStamp, String msisdn, String serviceId, ChargingRequest chargingRequest,
            int[] bucket, int[] counter, LocalDateTime counterD) {
        
        
        this.timeStamp = timeStamp;
        this.msisdn = msisdn;
        this.serviceId = serviceId;
        this.chargingRequest = chargingRequest;
        this.bucket = new int[3];
        this.bucket[0] = bucket[0];
        this.bucket[1] = bucket[1];
        this.bucket[2] = bucket[2];
        this.counter = new int[3];
        this.counter[0] = counter[0];
        this.counter[1] = counter[1];
        this.counter[2] = counter[2];
        this.counterD = counterD;
       
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    public String getMsisdn() {
        return msisdn;
    }
    public String getServiceId() {
        return serviceId;
    }
    public ChargingRequest getChargingRequest() {
        return chargingRequest;
    }
    public ChargingReply getChargingReply() {
        return chargingReply;
    }
    public int[] getBucket() {
        return bucket;
    }
    public int[] getCounter() {
        return counter;
    }
    public LocalDateTime getCounterD() {
        return counterD;
    }
    public String getAppliedTariff() {
        return appliedTariff;
    }
    
    @Override
    public String toString() {
        return "CDR [timeStamp=" + timeStamp + ", msisdn=" + msisdn + ", serviceId=" + serviceId + ", chargingRequest="
                + chargingRequest + ", chargingReply=" + chargingReply + ", bucket=" + Arrays.toString(bucket)
                + ", counter=" + Arrays.toString(counter) + ", counterD=" + counterD + ", appliedTariff="
                + appliedTariff + "]";
    }
}
