package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.dto.TradeDto;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.service.DividendService;
import org.pcasano.portfoliotracker.service.TradeService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class TradeController {

    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/trades")
    @ResponseBody
    public List<Trade> trades() {
        return tradeService.findAll();
    }

    @PostMapping("/create-trade")
    public Trade createTrade(@RequestBody TradeDto tradeDto) throws ParseException {
        return tradeService.create(
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
        );
    }

    @PostMapping("/create-trades")
    public List<Trade> createTrades(@RequestBody List<TradeDto> listOfTradeDto) throws ParseException {
        return tradeService.create(listOfTradeDto);
    }
}
