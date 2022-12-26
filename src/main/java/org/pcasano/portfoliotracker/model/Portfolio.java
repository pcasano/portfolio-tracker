package org.pcasano.portfoliotracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

    private String companyName;
    private String symbol;
    private Integer quantity;
    private double priceOriginalCurrency;
    private double valueOriginalCurrency;
    private double valueBaseCurrency;
    private String currency;
    private double currentPriceOriginalCurrency;

    public Portfolio(String companyName, String symbol, Integer quantity, double priceOriginalCurrency, double valueOriginalCurrency, double valueBaseCurrency, double currentPriceOriginalCurrency, String currency) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.priceOriginalCurrency = priceOriginalCurrency;
        this.valueOriginalCurrency = valueOriginalCurrency;
        this.valueBaseCurrency = valueBaseCurrency;
        this.currency = currency;
        this.currentPriceOriginalCurrency = currentPriceOriginalCurrency;
    }
}
