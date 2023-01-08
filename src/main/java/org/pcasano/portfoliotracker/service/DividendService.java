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
    private Map<String, Double> dividends2018 = new HashMap<>();
    private Map<String, Double> dividends2019 = new HashMap<>();
    private Map<String, Double> dividends2020 = new HashMap<>();
    private Map<String, Double> dividends2021 = new HashMap<>();
    private Map<String, Double> dividends2022 = new HashMap<>();
    private Map<String, Double> dividends2023 = new HashMap<>();

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


    public Map<String, Double> getDividends2018() {
        this.setMapOfDividendsGivenYear("2018", this.dividends2018);
        return this.dividends2018;
    }

    public Map<String, Double> getDividends2019() {
        this.setMapOfDividendsGivenYear("2019", this.dividends2019);
        return this.dividends2019;
    }

    public Map<String, Double> getDividends2020() {
        this.setMapOfDividendsGivenYear("2020", this.dividends2020);
        return this.dividends2020;
    }

    public Map<String, Double> getDividends2021() {
        this.setMapOfDividendsGivenYear("2021", this.dividends2021);
        return this.dividends2021;
    }

    public Map<String, Double> getDividends2022() {
        this.setMapOfDividendsGivenYear("2022", this.dividends2022);
        return this.dividends2022;
    }
    public Map<String, Double> getDividends2023() {
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

    private void setMapOfDividendsGivenYear(String year, Map<String, Double> dividends) {
        dividends.put("January", this.getDividendsGivenMonthAndYear(year, "January").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("February", this.getDividendsGivenMonthAndYear(year, "February").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("March", this.getDividendsGivenMonthAndYear(year, "March").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("April", this.getDividendsGivenMonthAndYear(year, "April").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("May", this.getDividendsGivenMonthAndYear(year, "May").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("June", this.getDividendsGivenMonthAndYear(year, "June").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("July", this.getDividendsGivenMonthAndYear(year, "July").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("August", this.getDividendsGivenMonthAndYear(year, "August").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("September", this.getDividendsGivenMonthAndYear(year, "September").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("October", this.getDividendsGivenMonthAndYear(year, "October").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("November", this.getDividendsGivenMonthAndYear(year, "November").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        dividends.put("December", this.getDividendsGivenMonthAndYear(year, "December").stream().mapToDouble(div -> div.getAmount() * div.getRate()).sum());
    }
}