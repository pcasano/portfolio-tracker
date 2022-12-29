package org.pcasano.portfoliotracker.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.security.MessageDigest;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Transient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


@Entity
@Table(name = "Dividends")
@Setter
@Getter
public class Dividend {

    @Id
    private String id;
    private String paymentDate;
    private String companyName;
    private double amount;
    private double tax;
    private String currency;
    private double rate;
    private String activityCode;
    private String month;
    private String year;
    private transient int divNr;

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
        this.id = DigestUtils.md2Hex(companyName+amount+paymentDate);
    }


    @Override
    public String toString() {
        return " + paymentDate" + paymentDate + ",companyName=" + companyName + ",amount=" + amount + ",rate=" + rate;
    }

    public Dividend() {
    }
}
