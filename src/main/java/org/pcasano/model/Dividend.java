package org.pcasano.model;

import java.util.Date;

public class Dividend {

    private final String paymentDate;
    private final String companyName;
    private final double amount;
    private double tax;
    private final String currency;
    private final double rate;
    private final String activityCode;

    public Dividend(String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) {
        this.paymentDate = paymentDate;
        this.companyName = companyName;
        this.amount = amount;
        this.tax = tax;
        this.currency = currency;
        this.rate = rate;
        this.activityCode = activityCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public String getActivityCode() {
        return activityCode;
    }
}
