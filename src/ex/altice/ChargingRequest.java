package ex.altice;

import java.sql.Timestamp;


public class ChargingRequest {
    private static int counter = 0; 
    private int requestId;
    private Timestamp timeStamp;
    private String service;
    private boolean isRoaming;
    private String msisdn;
    private int rsu;

    //Contrutor
    public ChargingRequest(String service, boolean isRoaming, String msisdn, int rsu){
        
        //assertions     
        assert(service.equals("A") || service.equals("B"));
        assert(msisdn.matches("^[1-9][0-9]{10}$"));
        assert(rsu>=0);
        

        this.requestId = counter++;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.service = service;
        this.isRoaming = isRoaming;
        this.msisdn = msisdn;
        this.rsu = rsu; 
    }
    
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getRequestId() {
        return requestId;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getService() {
        return service;
    }

    public boolean isRoaming() {
        return isRoaming;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public int getRsu() {
        return rsu;
    }

}
