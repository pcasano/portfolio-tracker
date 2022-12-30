package org.pcasano.portfoliotracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Entity
@Table(name = "Trades")
@Setter
@Getter
public class Trade {

    @Id
    private String tradeId;
    private String currency;
    private double rate;
    private String symbol;
    private String description;
    private String tradeDate;
    private double commission;
    private Integer quantity;
    private String buySell;
    private double priceOriginalCurrency;
    private double priceBaseCurrency;
    private double priceOperationOriginalCurrency;
    private double priceOperationBaseCurrency;
    private String month;
    private String year;
    private String country;
    private transient int tradeNr;

    public Trade() {
    }

    public Trade(String tradeId, String currency, double rate, String symbol, String description, String tradeDate, double commission, Integer quantity, String buySell, double priceOriginalCurrency, String country) throws ParseException {
        this.tradeId = tradeId;
        this.currency = currency;
        this.rate = rate;
        this.symbol = symbol;
        this.description = description;
        this.tradeDate = tradeDate;
        this.commission = commission;
        this.quantity = quantity;
        this.buySell = buySell;
        this.priceOriginalCurrency = priceOriginalCurrency;
        this.priceBaseCurrency = priceOriginalCurrency * (1 / rate);
        this.priceOperationOriginalCurrency = priceOriginalCurrency * quantity + commission;
        this.priceOperationBaseCurrency = this.priceOperationOriginalCurrency / rate;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(this.getTradeDate()));
        this.month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        this.year = Integer.valueOf(cal.get(Calendar.YEAR)).toString();
        this.country = country;
    }
}
