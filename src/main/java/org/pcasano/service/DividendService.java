package org.pcasano.service;

import org.pcasano.model.Dividend;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



@Component
public class DividendService {

        private List<Dividend> dividends = new CopyOnWriteArrayList<>();

        public List<Dividend> findAll() {
            return dividends;
        }

        public Dividend create(String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) {
            Dividend dividend = new Dividend(paymentDate, companyName, amount, tax, currency, rate, activityCode);
            dividends.add(dividend);
            return dividend;
        }

}
