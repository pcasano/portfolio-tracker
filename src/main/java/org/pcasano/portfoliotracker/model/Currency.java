package org.pcasano.portfoliotracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Currency")
@Setter
@Getter
public class Currency {

    @Id
    private String transactionId;
    private double amount;
    private double rate;
    private double targetAmount;
    private double fee;
    private String date;
    private String targetCurrency;
    private Integer nr;

    public Currency(String transactionId, double amount, double rate, double fee, String date, String targetCurrency) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.rate = rate;
        this.fee = fee;
        this.date = date;
        this.targetCurrency = targetCurrency;
        this.targetAmount = amount * rate;
    }

    public Currency() {
    }
}
