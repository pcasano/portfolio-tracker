package org.pcasano.portfoliotracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trend {

    private String symbol;
    private double price50AvgOriginalCurrency;
    private double price200AvgOriginalCurrency;
    private double marketPriceOriginalCurrency;
    private double changeInPrice50Avg;
    private double changeInPrice200Avg;
    private Integer nr;

    public Trend(String symbol, double marketPriceOriginalCurrency, double price50AvgOriginalCurrency, double price200AvgOriginalCurrency, double changeInPrice50Avg, double changeInPrice200Avg) {
        this.symbol = symbol;
        this.marketPriceOriginalCurrency = marketPriceOriginalCurrency;
        this.price50AvgOriginalCurrency = price50AvgOriginalCurrency;
        this.price200AvgOriginalCurrency = price200AvgOriginalCurrency;
        this.changeInPrice50Avg = changeInPrice50Avg;
        this.changeInPrice200Avg = changeInPrice200Avg;
    }
}
