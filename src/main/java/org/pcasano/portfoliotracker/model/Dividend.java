package org.pcasano.portfoliotracker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Dividend {
    private final String paymentDate;
    private final String companyName;
    private final double amount;
    private double tax;
    private final String currency;
    private final double rate;
    private final String activityCode;
    private final String month;
    private final String year;

    public Dividend(String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) throws ParseException {
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
