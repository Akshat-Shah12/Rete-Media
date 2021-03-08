package com.retemedia;

public class PaymentData {
    private String method,date;
    private long amount;
    public PaymentData(String method, String date, long amount)
    {
        this.method = method;
        this.date = date;
        this.amount = amount;
    }
    public String getMethod(){return method;}
    public String getDate(){return date;}
    public long getAmount(){return amount;}
}
