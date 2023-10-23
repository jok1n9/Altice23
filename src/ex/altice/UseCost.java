package ex.altice;

import java.sql.Timestamp;

public class UseCost {
    private Timestamp timeStamp;
    private int gsu;
    private double  cost;               
    private String msisdn;
    private double  finalPrice;
    
    public UseCost(Timestamp timeStamp, int gsu, double cost, String msisdn, double finalPrice) {
        this.timeStamp = timeStamp;
        this.gsu = gsu;
        this.cost = cost;
        this.msisdn = msisdn;
        this.finalPrice = finalPrice;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public int getGsu() {
        return gsu;
    }

    public double getCost() {
        return cost;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return String.format("%-19s | %-21d | %-10.2f | %-14s | %-10.2f",
            timeStamp, gsu, cost, msisdn, finalPrice);
    }


  
}
