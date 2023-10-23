package ex.altice;

public class ChargingReply {
    private int requestId;
    private String result;
    private int gsu;

    
    public ChargingReply(int requestId, String result, int gsu) {
        this.requestId = requestId;
        this.result = result;
        this.gsu = gsu;
    }


    public int getRequestId() {
        return requestId;
    }


    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public int getGsu() {
        return gsu;
    }


    public void setGsu(int gsu) {
        this.gsu = gsu;
    }
    
}
