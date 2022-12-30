package org.pcasano.portfoliotracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeDto {

    @JsonProperty("trade_id")
    private String tradeId;
    private String currency;
    private double rate;
    private String symbol;
    private String description;
    @JsonProperty("trade_date")
    private String tradeDate;
    private double commission;
    private Integer quantity;
    @JsonProperty("buy_sell")
    private String buySell;
    @JsonProperty("price_original_currency")
    private double priceOriginalCurrency;
    private String country;


}


