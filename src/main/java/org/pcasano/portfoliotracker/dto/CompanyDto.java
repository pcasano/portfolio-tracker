package org.pcasano.portfoliotracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {

    private String symbol;
    private String description;
    @JsonProperty("yahoo_symbol")
    private String yahooSymbol;

}
