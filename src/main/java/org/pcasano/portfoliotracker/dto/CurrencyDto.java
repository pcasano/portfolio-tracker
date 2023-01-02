package org.pcasano.portfoliotracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {


    @JsonProperty("transaction_id")
    private String transactionId;
    private double amount;
    private double rate;
    private double fee;
    private String date;
    @JsonProperty("target_currency")
    private String targetCurrency;


}
