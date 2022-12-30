package org.pcasano.portfoliotracker.service;

import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.dto.TradeDto;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    private List<Trade> trades = new CopyOnWriteArrayList<>();
    private Map<String, String> trades2018 = new HashMap<>();
    private Map<String, String> trades2019 = new HashMap<>();
    private Map<String, String> trades2020 = new HashMap<>();
    private Map<String, String> trades2021 = new HashMap<>();
    private Map<String, String> trades2022 = new HashMap<>();
    private Map<String, String> trades2023 = new HashMap<>();


    public List<Trade> findAll() {
        Iterator<Trade> tradeIterator = tradeRepository.findAll().iterator();
        List<Trade> tradeList = new ArrayList<>();
        while (tradeIterator.hasNext())
            tradeList.add(tradeIterator.next());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        tradeList.sort(Comparator.comparing((trade -> {
            try {
                return sdf.parse(trade.getTradeDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        })));

        for(int i=1; i<tradeList.size() + 1; i++) {
            tradeList.get(i - 1).setTradeNr(i);
        }
        Collections.reverse(tradeList);
        return tradeList;
    }

    public List<List<Object>> getPortfolioValueMap() {
        List<Trade> listOfTrades = this.findAll();
        Collections.reverse(listOfTrades);
        LinkedHashSet<String> dates = new LinkedHashSet<>(listOfTrades.stream().map(Trade::getTradeDate).toList());
        List<List<Object>> list = new ArrayList<>();
        double count = 0;
        for (String date:dates) {
            count = count + listOfTrades.stream()
                    .filter(trade -> trade.getTradeDate().equals(date))
                    .mapToDouble(trade -> trade.getQuantity() * trade.getPriceBaseCurrency()).sum();
            list.add(List.of(date, count));
        }
        return list;
    }

    public Trade create(String tradeId, String currency, double rate, String symbol, String description, String tradeDate, double commission, Integer quantity, String buySell, double priceOriginalCurrency, String country) throws ParseException {
        return tradeRepository.save(
                new Trade(
                        tradeId,
                        currency,
                        rate,
                        symbol,
                        description,
                        tradeDate,
                        commission,
                        quantity,
                        buySell,
                        priceOriginalCurrency,
                        country
                ));
    }


    public List<Trade> create(List<TradeDto> listOfTradesDto) throws ParseException {
        List<Trade> listOfTrades = new ArrayList<>();
        for (TradeDto tradeDto : listOfTradesDto) {
            listOfTrades.add(
                    new Trade(
                            tradeDto.getTradeId(),
                            tradeDto.getCurrency(),
                            tradeDto.getRate(),
                            tradeDto.getSymbol(),
                            tradeDto.getDescription(),
                            tradeDto.getTradeDate(),
                            tradeDto.getCommission(),
                            tradeDto.getQuantity(),
                            tradeDto.getBuySell(),
                            tradeDto.getPriceOriginalCurrency(),
                            tradeDto.getCountry()
                    ));
        }
        tradeRepository.saveAll(listOfTrades);
        return listOfTrades;
    }

    public Map<String, String> getTrades2018() {
        this.setMapOfDividendsGivenYear("2018", this.trades2018);
        return this.trades2018;
    }

    public Map<String, String> getTrades2019() {
        this.setMapOfDividendsGivenYear("2019", this.trades2019);
        return this.trades2019;
    }

    public Map<String, String> getTrades2020() {
        this.setMapOfDividendsGivenYear("2020", this.trades2020);
        return this.trades2020;
    }

    public Map<String, String> getTrades2021() {
        this.setMapOfDividendsGivenYear("2021", this.trades2021);
        return this.trades2021;
    }

    public Map<String, String> getTrades2022() {
        this.setMapOfDividendsGivenYear("2022", this.trades2022);
        return this.trades2022;
    }
    public Map<String, String> getTrades2023() {
        this.setMapOfDividendsGivenYear("2023", this.trades2023);
        return this.trades2023;
    }

    private List<Trade> getTradesGivenMonthAndYear(String year, String month) {
        return this.findAll().stream().filter(div -> div.getYear().equals(year) && div.getMonth().equals(month)).toList();
    }

    private void setMapOfDividendsGivenYear(String year, Map<String, String> trades) {
        trades.put("January", Double.toString(this.getTradesGivenMonthAndYear(year, "January").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("February", Double.toString(this.getTradesGivenMonthAndYear(year, "February").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("March", Double.toString(this.getTradesGivenMonthAndYear(year, "March").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("April", Double.toString(this.getTradesGivenMonthAndYear(year, "April").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("May", Double.toString(this.getTradesGivenMonthAndYear(year, "May").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("June", Double.toString(this.getTradesGivenMonthAndYear(year, "June").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("July", Double.toString(this.getTradesGivenMonthAndYear(year, "July").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("August", Double.toString(this.getTradesGivenMonthAndYear(year, "August").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("September", Double.toString(this.getTradesGivenMonthAndYear(year, "September").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("October", Double.toString(this.getTradesGivenMonthAndYear(year, "October").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("November", Double.toString(this.getTradesGivenMonthAndYear(year, "November").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
        trades.put("December", Double.toString(this.getTradesGivenMonthAndYear(year, "December").stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum()));
    }
}
