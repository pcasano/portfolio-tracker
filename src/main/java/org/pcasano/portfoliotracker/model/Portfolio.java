package org.pcasano.portfoliotracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

    private String companyName;
    private String symbol;
    private Integer quantity;
    private double openPriceOriginalCurrency;
    private double marketPriceOriginalCurrency;
    private double openValueOriginalCurrency;
    private double marketValueBaseCurrency;
    private String currency;

    private double diffValueBaseCurrency;

    private Integer nr;

    public Portfolio(String companyName, String symbol, Integer quantity, double openPriceOriginalCurrency, double openValueOriginalCurrency, double marketValueBaseCurrency, double marketPriceOriginalCurrency, String currency, double diffValueBaseCurrency) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.openPriceOriginalCurrency = openPriceOriginalCurrency;
        this.openValueOriginalCurrency = openValueOriginalCurrency;
        this.marketValueBaseCurrency = marketValueBaseCurrency;
        this.currency = currency;
        this.marketPriceOriginalCurrency = marketPriceOriginalCurrency;
        this.diffValueBaseCurrency = diffValueBaseCurrency;
    }
}
