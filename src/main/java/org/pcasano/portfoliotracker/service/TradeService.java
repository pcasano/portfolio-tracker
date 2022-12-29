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

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;


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
        Collections.reverse(tradeList);
        return tradeList;
    }

    public List<List<Object>> getPortfolioValueMap() {
        List<Trade> listOfTrades = this.findAll();
        Collections.reverse(listOfTrades);
        LinkedHashSet<String> dates = new LinkedHashSet<>(listOfTrades.stream().map(Trade::getTradeDate).toList());
        //LinkedHashMap<String, Double> mapOfPortfolioValue = new LinkedHashMap<>();
        List<List<Object>> list = new ArrayList<>();
        double count = 0;
        for (String date:dates) {
            count = count + listOfTrades.stream()
                    .filter(trade -> trade.getTradeDate().equals(date))
                    .mapToDouble(trade -> trade.getQuantity() * trade.getPriceBaseCurrency()).sum();
            //mapOfPortfolioValue.put(date, count);
            list.add(List.of(date, count));
        }
        return list;
    }

    public Trade create(String tradeId, String currency, double rate, String symbol, String description, String tradeDate, String commission, Integer quantity, String buySell, double priceOriginalCurrency) throws ParseException {
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
                        priceOriginalCurrency
                ));
    }


    public List<Trade> create(List<TradeDto> listOfTradesDto) throws ParseException {
        List<Trade> listOfTrades = new ArrayList<>();
        for (TradeDto tradeDto : listOfTradesDto) {
            listOfTrades.add(
                    new Trade(
                            tradeDto.getTradeDate(),
                            tradeDto.getCurrency(),
                            tradeDto.getRate(),
                            tradeDto.getSymbol(),
                            tradeDto.getDescription(),
                            tradeDto.getTradeDate(),
                            tradeDto.getCommission(),
                            tradeDto.getQuantity(),
                            tradeDto.getBuySell(),
                            tradeDto.getPriceOriginalCurrency()
                    ));
        }
        tradeRepository.saveAll(listOfTrades);
        return listOfTrades;
    }


}
