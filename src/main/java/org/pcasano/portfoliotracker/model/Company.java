package org.pcasano.portfoliotracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Companies")
@Setter
@Getter
public class Company {

    @Id
    private String symbol;
    private String description;
    private String yahooSymbol;


    public Company() {
    }

    public Company(String symbol, String description, String yahooSymbol) {
        this.symbol = symbol;
        this.description = description;
        this.yahooSymbol = yahooSymbol;
    }
}
