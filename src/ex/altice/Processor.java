package ex.altice;

import java.sql.Timestamp;

public class Processor {
    
    private ChargingRequest request;
    private BillingAccount account;
    private ChargingReply reply;
    private CDR requestRecord;
    private CDR replyRecord;
    private String  message = "OK";     //o default é OK muda caso não seja elegível
    private double  cost;               //rate
    private int     bucket;             // 0 bucket1 1 bucket2 2 bucket3
    private int     gsu;
    private String  tariffService;
    private double  finalPrice;

    public Processor(ChargingRequest request, BillingAccount account)
    {
        assert(request.getMsisdn() == account.getMsisdn());

        this.request = request;
        this.account = account;
        this.cost = 0;
        this.gsu = 0;
        this.bucket = -1;
        this.finalPrice = 0;
        this.message = "OK";
        if(request.getService().equals("A")) tariffService = account.getTariffServiceA();
        else tariffService= account.getTariffServiceB();
    }

    public int getGsu() {
        return gsu;
    }

    public double getCost() {
        return cost;
    }

    public ChargingReply getReply() {
        return reply;
    }

    public int getBucket() {
        return bucket;
    }

    public CDR getRequestRecord() {
        return requestRecord;
    }

    public CDR getReplyRecord() {
        return replyRecord;
    }

    public UseCost getUseCost(){
        return new UseCost(request.getTimeStamp(), gsu, cost, request.getMsisdn(), finalPrice);
    }

    public String getTariffService() {
        return tariffService;
    }

    public String getMessage() {
        return message;
    }

    public ChargingReply processRequest()
    {
        requestRecord= createRequestRecord();
        
        verifyEligibility(request, account);
        
        if(message.equals("OK"))
        {
            checkRating(request, account);
            
            double bucketMoney = centToEur(account.getBucket(this.bucket)); //dinheiro no bucket que vai ser usado
            this.finalPrice = this.cost * request.getRsu();
            
            if(bucketMoney >= finalPrice) 
            {
                this.gsu= request.getRsu();
                
                account.doCharge(request.getTimeStamp(), this.bucket, finalPrice, tariffService, request.isRoaming(), gsu);
            }
            else if(bucketMoney >= this.cost) // não fornece todas as service units requisitadas mas apenas as que o saldo permite
            {
                
                this.gsu = (int)(bucketMoney / cost);
                finalPrice = this.gsu * this.cost;
                account.doCharge(request.getTimeStamp(), this.bucket, finalPrice, tariffService, request.isRoaming(), gsu);
            }
            else {
                this.message = "CreditLimitReached";
            }
        }
        
        reply= new ChargingReply(request.getRequestId(), message, gsu);
        
        replyRecord = createReplyRecord();

        return reply;
    }
    
    public CDR createRequestRecord()
    {
        return new CDR(request.getTimeStamp(), request.getMsisdn(), request.getService(), request, account.getBuckets(), 
                        account.getCounters(), account.getCounterD());
    }

    public CDR createReplyRecord()
    
    {
        return new CDR(request.getTimeStamp(), request.getMsisdn(), request.getService(), reply, account.getBuckets(), 
                        account.getCounters(), account.getCounterD(), tariffService);
    }
    
    //verifica a eligibilidade(tempo e buckets) do request
    public void verifyEligibility(ChargingRequest request, BillingAccount account)
    {
        if(request.getService().equals("A"))
        {
            switch(account.getTariffServiceA()){
                case "Alpha1":
                    if(isWeekend(request.getTimeStamp()) || account.getCounterA()>=100) 
                        this.message="Não elegível: tarifário sem Roaming ou uso aos fins de semana";
                    break;
                case "Alpha2":
                    if(request.isRoaming() && account.getBucket2()>10) this.message = "Não elegível";
                    break;
                case "Alpha3":
                    if(!request.isRoaming() && account.getBucket3()>10) this.message = "Não elegível";
                    break;
                default:
                    this.message = "Não elegível"; 
            }
        }
        else if(request.getService().equals("B"))
        {
            switch(account.getTariffServiceB()){
                case "Beta1":
                    if(isWeekend(request.getTimeStamp()) && !isNight(request.getTimeStamp())) this.message = "Não elegível";
                    break;
                case "Beta2":
                    if(request.isRoaming() && account.getBucket2()>10) this.message = "Não elegível";
                    break;
                case "Beta3":
                    if(!request.isRoaming() && account.getBucket3()>10) this.message = "Não elegível";
                    break;
                default:
                    this.message = "Não elegível"; 
            }
        }
    }


    //encontra o preço e desconto por unidade
    public void checkRating(ChargingRequest request, BillingAccount account)
    {
        if(request.getService().equals("A"))
        {
            switch(account.getTariffServiceA()){
                case "Alpha1":
                    //rating
                    if(request.isRoaming()) this.cost = 2;
                    else if(isNight(request.getTimeStamp()))this.cost = 0.5;        
                    else this.cost = 1;
                    
                    //desconto... pode acumular
                    if(account.getCounterA()>10) this.cost -= 0.25; 
                    if(account.getBucket3()>50) this.cost -= 0.1;

                    //cobrança
                    if(request.isRoaming() && account.getCounterB()>5) this.bucket = 2;
                    if(request.isRoaming()) this.bucket = 1;
                    else this.bucket = 0;


                    break;
                case "Alpha2":
                    //rating
                    if(isNight(request.getTimeStamp())) this.cost = 0.25;
                    else this.cost= 0.5;

                    //desconto
                    if(account.getCounterB()>10) this.cost -= 0.2;
                    if(account.getBucket2()>15) this.cost -= 0.05;

                    //cobranca
                    this.bucket = 1;

                    break;
                case "Alpha3":
                    //rating
                    if(isWeekend(request.getTimeStamp())) this.cost = 0.25;
                    else this.cost = 1;

                    //desconto
                    if(account.getCounterB()>10) this.cost -= 0.2;
                    if(account.getBucket2()>15) this.cost -= 0.05;

                    //cobrança
                    this.bucket = 2;

                    break;
                default:
                    this.message = "Não elegível"; 
            }
        }
        else if(request.getService().equals("B"))
        {
            switch(account.getTariffServiceB()){
                case "Beta1":
                    //rating
                    if(request.isRoaming()) this.cost = 0.2;
                    else if(isNight(request.getTimeStamp())) this.cost = 0.05;
                    else this.cost = 0.1;
                    
                    //desconto
                    if(account.getCounterA()>10) this.cost -= 0.025;
                    if(account.getBucket3()>50) this.cost -= 0.01;
                    
                    //cobranca
                    if(!request.isRoaming()) this.bucket = 0;
                    else if(account.getCounterB()>5) this.bucket = 1;
                    else this.bucket = 2;


                    break;
                case "Beta2":
                    //rating
                    if(isNight(request.getTimeStamp())) this.cost = 0.025;
                    else this.cost = 0.05;

                    //desconto
                    if(account.getCounterB()>10) this.cost -=0.02;
                    if(account.getBucket2()>15) this.cost -= 0.005;
                    
                    //cobranca
                    this.bucket = 1;
                    
                    break;
                case "Beta3":
                    //rating
                    if(isWeekend(request.getTimeStamp())) this.cost = 0.025;
                    else this.cost = 0.1;

                    //desconto
                    if(account.getCounterB()>10) this.cost -= 0.02;
                    if(account.getBucket2()>15) this.cost -= 0.005;

                    //cobrança
                    this.bucket = 2;

                    break;
                default:
                    this.message = "Não elegível"; 
            }
        }
    }


    //fim de semana boolean
    private boolean isWeekend(Timestamp timestamp){
        int day = timestamp.toLocalDateTime().getDayOfWeek().getValue();
        return day == 6 || day == 7;
    }
    
    //é de noite boolean
    private boolean isNight(Timestamp timestamp)
    {
        int hour = timestamp.toLocalDateTime().getHour();
        return hour >= 22 || hour < 6;
    }

    private double centToEur(int cents){
        return cents/100;
    }
}