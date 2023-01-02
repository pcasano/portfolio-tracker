package org.pcasano.portfoliotracker.service;

import org.pcasano.portfoliotracker.dto.CompanyDto;
import org.pcasano.portfoliotracker.dto.CurrencyDto;
import org.pcasano.portfoliotracker.model.Company;
import org.pcasano.portfoliotracker.model.Currency;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;


    public List<Currency> findAll() {
        Iterator<Currency> currencyIterator = currencyRepository.findAll().iterator();
        List<Currency> currencyList = new ArrayList<>();
        while (currencyIterator.hasNext())
            currencyList.add(currencyIterator.next());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currencyList.sort(Comparator.comparing((currency -> {
            try {
                return sdf.parse(currency.getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        })));

        for(int i=1; i<currencyList.size() + 1; i++) {
            currencyList.get(i - 1).setNr(i);
        }
        Collections.reverse(currencyList);
        return currencyList;
    }

    public List<Currency> create(List<CurrencyDto> listOfCurrencyDto) throws ParseException {
        List<Currency> listOfCurrency = new ArrayList<>();
        for (CurrencyDto currencyDto : listOfCurrencyDto) {
            listOfCurrency.add(
                new Currency(
                        currencyDto.getTransactionId(),
                        currencyDto.getAmount(),
                        currencyDto.getRate(),
                        currencyDto.getFee(),
                        currencyDto.getDate(),
                        currencyDto.getTargetCurrency()
                ));
        }
        currencyRepository.saveAll(listOfCurrency);
        return listOfCurrency;
    }
}
