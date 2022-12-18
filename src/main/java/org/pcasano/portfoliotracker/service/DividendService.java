package org.pcasano.portfoliotracker.service;

import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.model.Dividend;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;



@Component
public class DividendService {

    private List<Dividend> dividends = new CopyOnWriteArrayList<>();
    private Map<String, String> dividends2018 = new HashMap<>();
    private Map<String, String> dividends2019 = new HashMap<>();
    private Map<String, String> dividends2020 = new HashMap<>();
    private Map<String, String> dividends2021 = new HashMap<>();
    private Map<String, String> dividends2022 = new HashMap<>();


/*    public List<Dividend> findAll() {
        return dividends;
    }*/

        public List<Dividend> findAll() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            dividends.sort(Comparator.comparing((div -> {
                try {
                    return sdf.parse(div.getPaymentDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            })));
            Collections.reverse(dividends);
            return dividends;
        }


    public Map<String, String> getDividends2018() {
        this.setMapOfDividendsGivenYear("2018", this.dividends2018);
        return this.dividends2018;
    }

    public Map<String, String> getDividends2019() {
        this.setMapOfDividendsGivenYear("2019", this.dividends2019);
        return this.dividends2019;
    }

    public Map<String, String> getDividends2020() {
        this.setMapOfDividendsGivenYear("2020", this.dividends2020);
        return this.dividends2020;
    }

    public Map<String, String> getDividends2021() {
        this.setMapOfDividendsGivenYear("2021", this.dividends2021);
        return this.dividends2021;
    }

    public Map<String, String> getDividends2022() {
        this.setMapOfDividendsGivenYear("2022", this.dividends2022);
        return this.dividends2022;
    }


    public Dividend create(String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) throws ParseException {
        Dividend dividend = new Dividend(paymentDate, companyName, amount, tax, currency, rate, activityCode);
        dividends.add(dividend);
        return dividend;
    }

    public List<Dividend> create(List<DividendDto> listOfDividendDto) throws ParseException {
        List<Dividend> listOfDividends = new ArrayList<>();
        for (DividendDto dividendDto : listOfDividendDto) {
            listOfDividends.add(
                    new Dividend(
                            dividendDto.getPaymentDate(),
                            dividendDto.getCompanyName(),
                            dividendDto.getAmount(),
                            dividendDto.getTax(),
                            dividendDto.getCurrency(),
                            dividendDto.getRate(),
                            dividendDto.getActivityCode()));
        }
        dividends.addAll(listOfDividends);
        listOfDividends.forEach(div -> System.out.println(div.getMonth()));
        return listOfDividends;
    }

    private List<Dividend> getDividendsGivenMonthAndYear(String year, String month) {
        return dividends.stream().filter(div -> div.getYear().equals(year) && div.getMonth().equals(month)).toList();
    }

    private void setMapOfDividendsGivenYear(String year, Map<String, String> dividends) {
        dividends.put("January", Double.toString(this.getDividendsGivenMonthAndYear(year, "January").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("February", Double.toString(this.getDividendsGivenMonthAndYear(year, "February").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("March", Double.toString(this.getDividendsGivenMonthAndYear(year, "March").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("April", Double.toString(this.getDividendsGivenMonthAndYear(year, "April").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("May", Double.toString(this.getDividendsGivenMonthAndYear(year, "May").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("June", Double.toString(this.getDividendsGivenMonthAndYear(year, "June").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("July", Double.toString(this.getDividendsGivenMonthAndYear(year, "July").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("August", Double.toString(this.getDividendsGivenMonthAndYear(year, "August").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("September", Double.toString(this.getDividendsGivenMonthAndYear(year, "September").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("October", Double.toString(this.getDividendsGivenMonthAndYear(year, "October").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("November", Double.toString(this.getDividendsGivenMonthAndYear(year, "November").stream().mapToDouble(Dividend::getAmount).sum()));
        dividends.put("December", Double.toString(this.getDividendsGivenMonthAndYear(year, "December").stream().mapToDouble(Dividend::getAmount).sum()));
    }
}