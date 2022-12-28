package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.model.Portfolio;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.service.DividendService;
import org.pcasano.portfoliotracker.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class WebsiteController {

    public WebsiteController(DividendService dividendService, TradeService tradeService) {
        this.dividendService = dividendService;
        this.tradeService = tradeService;
    }

    private DividendService dividendService;
    private TradeService tradeService;

    @GetMapping("/dividends-html")
    public String getDividendPage(Model model) {
        model.addAttribute("mapOfDividends2018", dividendService.getDividends2018());
        model.addAttribute("mapOfDividends2019", dividendService.getDividends2019());
        model.addAttribute("mapOfDividends2020", dividendService.getDividends2020());
        model.addAttribute("mapOfDividends2021", dividendService.getDividends2021());
        model.addAttribute("mapOfDividends2022", dividendService.getDividends2022());
        model.addAttribute("mapOfDividends2023", dividendService.getDividends2023());
        model.addAttribute("dividends2018", dividendService.findAll().stream().filter(div -> div.getYear().equals("2018")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2019", dividendService.findAll().stream().filter(div -> div.getYear().equals("2019")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2020", dividendService.findAll().stream().filter(div -> div.getYear().equals("2020")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2021", dividendService.findAll().stream().filter(div -> div.getYear().equals("2021")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2022", dividendService.findAll().stream().filter(div -> div.getYear().equals("2022")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2023", dividendService.findAll().stream().filter(div -> div.getYear().equals("2023")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends", dividendService.findAll());
        return "dividendPage.html";
    }

    @GetMapping("/trades-html")
    public String getTradePage(Model model) {
        model.addAttribute("mapOfDividends2018", dividendService.getDividends2018());
        model.addAttribute("mapOfDividends2019", dividendService.getDividends2019());
        model.addAttribute("mapOfDividends2020", dividendService.getDividends2020());
        model.addAttribute("mapOfDividends2021", dividendService.getDividends2021());
        model.addAttribute("mapOfDividends2022", dividendService.getDividends2022());
        model.addAttribute("mapOfDividends2023", dividendService.getDividends2023());
        model.addAttribute("dividends2018", dividendService.findAll().stream().filter(div -> div.getYear().equals("2018")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2019", dividendService.findAll().stream().filter(div -> div.getYear().equals("2019")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2020", dividendService.findAll().stream().filter(div -> div.getYear().equals("2020")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2021", dividendService.findAll().stream().filter(div -> div.getYear().equals("2021")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2022", dividendService.findAll().stream().filter(div -> div.getYear().equals("2022")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("dividends2023", dividendService.findAll().stream().filter(div -> div.getYear().equals("2023")).mapToDouble(div -> div.getAmount() * div.getRate()).sum());
        model.addAttribute("trades", tradeService.findAll());
        return "tradePage.html";
    }

    @GetMapping("/portfolio-html")
    public String getPortfolioPage(Model model) {
        List<Portfolio> portfolioList = new ArrayList<>();
        List<Trade> tradeList = tradeService.findAll();
        Set<String> setOfSymbols = new HashSet<>(tradeList.stream().map(Trade::getSymbol).toList());
        setOfSymbols.forEach(symbol -> {
            int quantity = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).mapToInt(Trade::getQuantity).sum();
            if (quantity > 0) {
                List<Trade> filteredTradeList = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).toList();
                try {
                    portfolioList.add(new Portfolio(
                            filteredTradeList.stream().map(Trade::getDescription).findFirst().orElse("N/A"),
                            symbol,
                            quantity,
                            filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency()).sum() / quantity,
                            filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency()).sum(),
                            filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency() * trade.getRate()).sum(),
                            YahooFinance.get(symbol).getQuote().getPrice().doubleValue(),
                            filteredTradeList.stream().map(Trade::getCurrency).findFirst().orElse("N/A")
                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //YahooFinance.get(objectCompany.toString()).getQuote().price

        model.addAttribute("portfolio", portfolioList);
        return "portfolioPage.html";
    }
}