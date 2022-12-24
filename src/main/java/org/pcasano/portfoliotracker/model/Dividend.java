package org.pcasano.portfoliotracker.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Entity
@Table(name = "Dividend")
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String paymentDate;
    private String companyName;
    private double amount;
    private double tax;
    private String currency;
    private double rate;
    private String activityCode;
    private String month;
    private String year;

    public Dividend(Integer id, String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) throws ParseException {
        this.id = id;
        this.paymentDate = paymentDate;
        this.companyName = companyName;
        this.amount = amount;
        this.tax = tax;
        this.currency = currency;
        this.rate = rate;
        this.activityCode = activityCode;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(this.paymentDate));
        this.month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        this.year = Integer.valueOf(cal.get(Calendar.YEAR)).toString();
    }

/*    public Dividend(Integer id, String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.companyName = companyName;
        this.amount = amount;
        this.tax = tax;
        this.currency = currency;
        this.rate = rate;
        this.activityCode = activityCode;
        this.month = month;
        this.year = year;
    }*/

    public Dividend() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
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

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
