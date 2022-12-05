package org.pcasano.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class DividendDto {

    @JsonProperty("payment_date")
    private String paymentDate;
    @JsonProperty("company_name")
    private String companyName;
    private double amount;
    private double tax;
    private String currency;
    private double rate;
    @JsonProperty("activity_code")
    private String activityCode;


    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setActivityCode(String activityCode) {
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
