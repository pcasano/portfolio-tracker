package org.pcasano.portfoliotracker.service;

import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.repository.DividendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;



@Service
public class DividendService {

    @Autowired
    private DividendRepository dividendRepository;

    private List<Dividend> dividends = new CopyOnWriteArrayList<>();
    private Map<String, String> dividends2018 = new HashMap<>();
    private Map<String, String> dividends2019 = new HashMap<>();
    private Map<String, String> dividends2020 = new HashMap<>();
    private Map<String, String> dividends2021 = new HashMap<>();
    private Map<String, String> dividends2022 = new HashMap<>();
    private Map<String, String> dividends2023 = new HashMap<>();

        public List<Dividend> findAll() {
            Iterator<Dividend> dividendIterator = dividendRepository.findAll().iterator();
            List<Dividend> dividendList = new ArrayList<>();
            while (dividendIterator.hasNext())
                dividendList.add(dividendIterator.next());

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            dividendList.sort(Comparator.comparing((div -> {
                try {
                    return sdf.parse(div.getPaymentDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            })));
            for(int i=1; i<dividendList.size() + 1; i++) {
                dividendList.get(i - 1).setDivNr(i);
            }
            Collections.reverse(dividendList);
            return dividendList;
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
    public Map<String, String> getDividends2023() {
        this.setMapOfDividendsGivenYear("2023", this.dividends2023);
        return this.dividends2023;
    }

    public Dividend create(String paymentDate, String companyName, double amount, double tax, String currency, double rate, String activityCode) throws ParseException {
        Dividend dividend = new Dividend(paymentDate, companyName, amount, tax, currency, rate, activityCode);
        return dividendRepository.save(dividend);
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
        dividendRepository.saveAll(listOfDividends);
        return listOfDividends;
    }

    private List<Dividend> getDividendsGivenMonthAndYear(String year, String month) {
        return this.findAll().stream().filter(div -> div.getYear().equals(year) && div.getMonth().equals(month)).toList();
    }

    private void setMapOfDividendsGivenYear(String year, Map<String, String> dividends) {
        dividends.put("January", Double.toString(this.getDividendsGivenMonthAndYear(year, "January").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("February", Double.toString(this.getDividendsGivenMonthAndYear(year, "February").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("March", Double.toString(this.getDividendsGivenMonthAndYear(year, "March").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("April", Double.toString(this.getDividendsGivenMonthAndYear(year, "April").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("May", Double.toString(this.getDividendsGivenMonthAndYear(year, "May").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("June", Double.toString(this.getDividendsGivenMonthAndYear(year, "June").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("July", Double.toString(this.getDividendsGivenMonthAndYear(year, "July").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("August", Double.toString(this.getDividendsGivenMonthAndYear(year, "August").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("September", Double.toString(this.getDividendsGivenMonthAndYear(year, "September").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("October", Double.toString(this.getDividendsGivenMonthAndYear(year, "October").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("November", Double.toString(this.getDividendsGivenMonthAndYear(year, "November").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
        dividends.put("December", Double.toString(this.getDividendsGivenMonthAndYear(year, "December").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum()));
    }
}