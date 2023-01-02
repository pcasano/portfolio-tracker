package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.dto.CurrencyDto;
import org.pcasano.portfoliotracker.model.Currency;
import org.pcasano.portfoliotracker.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


@RestController
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/create-currencies")
    public List<Currency> createCurrency(@RequestBody List<CurrencyDto> listOfCurrencyDto) throws ParseException {
        return currencyService.create(listOfCurrencyDto);
    }
}
