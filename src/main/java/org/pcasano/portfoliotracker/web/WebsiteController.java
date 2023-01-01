package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.model.Portfolio;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.service.CompanyService;
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

    public WebsiteController(DividendService dividendService, TradeService tradeService, CompanyService companyService) {
        this.dividendService = dividendService;
        this.tradeService = tradeService;
        this.companyService = companyService;

    }

    private DividendService dividendService;
    private TradeService tradeService;
    private CompanyService companyService;

    @GetMapping("/dividends-html")
    public String getDividendPage(Model model) {
        model.addAttribute("mapOfDividends2018", dividendService.getDividends2018());
        model.addAttribute("mapOfDividends2019", dividendService.getDividends2019());
        model.addAttribute("mapOfDividends2020", dividendService.getDividends2020());
        model.addAttribute("mapOfDividends2021", dividendService.getDividends2021());
        model.addAttribute("mapOfDividends2022", dividendService.getDividends2022());
        model.addAttribute("mapOfDividends2023", dividendService.getDividends2023());
        model.addAttribute("dividends2018", dividendService.findAll().stream().filter(div -> div.getYear().equals("2018")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends2019", dividendService.findAll().stream().filter(div -> div.getYear().equals("2019")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends2020", dividendService.findAll().stream().filter(div -> div.getYear().equals("2020")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends2021", dividendService.findAll().stream().filter(div -> div.getYear().equals("2021")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends2022", dividendService.findAll().stream().filter(div -> div.getYear().equals("2022")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends2023", dividendService.findAll().stream().filter(div -> div.getYear().equals("2023")).mapToDouble(div -> div.getAmount() / div.getRate()).sum());
        model.addAttribute("dividends", dividendService.findAll());
        return "dividendPage.html";
    }

    @GetMapping("/trades-html")
    public String getTradePage(Model model) {
        model.addAttribute("mapOfTrades2018", tradeService.getTrades2018());
        model.addAttribute("mapOfTrades2019", tradeService.getTrades2019());
        model.addAttribute("mapOfTrades2020", tradeService.getTrades2020());
        model.addAttribute("mapOfTrades2021", tradeService.getTrades2021());
        model.addAttribute("mapOfTrades2022", tradeService.getTrades2022());
        model.addAttribute("mapOfTrades2023", tradeService.getTrades2023());
        model.addAttribute("trades2018", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2018")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades2019", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2019")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades2020", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2020")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades2021", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2021")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades2022", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2022")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades2023", tradeService.findAll().stream().filter(trade -> trade.getYear().equals("2023")).mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("trades", tradeService.findAll());
        model.addAttribute("tradeCounter", tradeService.getPortfolioValueMap());
        return "tradePage.html";
    }

    @GetMapping("/portfolio-html")
    public String getPortfolioPage(Model model) throws IOException {
        List<Portfolio> portfolioList = new ArrayList<>();
        List<Trade> tradeList = tradeService.findAll();
        List<List<Object>> listOfMarketValuePositionsBaseCurrency = new ArrayList<>();
        Set<String> setOfSymbols = new HashSet<>(tradeList.stream().map(Trade::getSymbol).toList());
        double eurUsdRatio = YahooFinance.get("EURUSD=X").getQuote().getPrice().doubleValue();
        double eurGbpRatio = YahooFinance.get("EURGBP=X").getQuote().getPrice().doubleValue();

        setOfSymbols.forEach(symbol -> {
            int quantity = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).mapToInt(Trade::getQuantity).sum();
            if (quantity > 0) {
                List<Trade> filteredTradeList = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).toList();
                try {
                    String currency = filteredTradeList.stream().map(Trade::getCurrency).findFirst().orElse("N/A");
                    double marketPriceOriginalCurrency = getCurrentPriceOriginalCurrency(currency, symbol);
                    double openPriceOriginalCurrency = filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency()).sum() / quantity;
                    double marketValueBaseCurrency = filteredTradeList
                            .stream().
                            mapToDouble(trade -> trade.getQuantity() * marketPriceOriginalCurrency / this.getCurrentRatio(currency, eurUsdRatio, eurGbpRatio)).sum();
                    listOfMarketValuePositionsBaseCurrency.add(List.of(symbol, marketValueBaseCurrency));
                    portfolioList.add(new Portfolio(
                            filteredTradeList.stream().map(Trade::getDescription).findFirst().orElse("N/A"),
                            symbol,
                            quantity,
                            openPriceOriginalCurrency,
                            filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency()).sum(),
                            marketValueBaseCurrency,
                            marketPriceOriginalCurrency,
                            currency,
                            quantity * (marketPriceOriginalCurrency - openPriceOriginalCurrency) / this.getCurrentRatio(currency, eurUsdRatio, eurGbpRatio)
                    ));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        portfolioList.sort(Comparator.comparing(Portfolio::getCompanyName));
        for(int i=1; i<portfolioList.size() + 1; i++) {
            portfolioList.get(i - 1).setNr(i);
        }

        double marketValueEurAllPositions = portfolioList.stream().mapToDouble(Portfolio::getMarketValueBaseCurrency).sum();
        List<List<Object>> listOfMarketValuePositionsBaseCurrencyProcent = new ArrayList<>();
        for(List<Object> listValues:listOfMarketValuePositionsBaseCurrency) {
            listOfMarketValuePositionsBaseCurrencyProcent.add(List.of(listValues.get(0), 100 * (Double) listValues.get(1) / marketValueEurAllPositions));
        }
        listOfMarketValuePositionsBaseCurrencyProcent.sort(Comparator.comparingDouble(value -> (Double) value.get(1)));
        Collections.reverse(listOfMarketValuePositionsBaseCurrencyProcent);


        model.addAttribute("portfolio", portfolioList);
        model.addAttribute("openValueOnlyEurPositions", portfolioList.stream().filter(por -> por.getCurrency().equals("EUR")).mapToDouble(Portfolio::getOpenValueOriginalCurrency).sum());
        model.addAttribute("openValueOnlyUsdPositions", portfolioList.stream().filter(por -> por.getCurrency().equals("USD")).mapToDouble(Portfolio::getOpenValueOriginalCurrency).sum());
        model.addAttribute("openValueOnlyGbpPositions", portfolioList.stream().filter(por -> por.getCurrency().equals("GBP")).mapToDouble(Portfolio::getOpenValueOriginalCurrency).sum());
        model.addAttribute("openValueEurAllPositions", tradeList.stream().mapToDouble(Trade::getPriceOperationBaseCurrency).sum());
        model.addAttribute("marketValueEurAllPositions", marketValueEurAllPositions);
        model.addAttribute("listOfMarketValuePositionsBaseCurrency", listOfMarketValuePositionsBaseCurrency);
        model.addAttribute("listOfMarketValuePositionsBaseCurrencyProcent", listOfMarketValuePositionsBaseCurrencyProcent);
        return "portfolioPage.html";
    }

    private String getYahooSymbol(String symbol) {
        return companyService.findAll().stream().filter(company -> company.getSymbol().equals(symbol)).findFirst().get().getYahooSymbol();
    }

    private double getCurrentPriceOriginalCurrency(String currency, String symbol) throws IOException {
        //double price = YahooFinance.get(getYahooSymbol(symbol)).getQuote().getPrice().doubleValue();
        double price = 22.7;
        return currency.equals("GBP") ? price/100 : price;
    }

    private double getCurrentRatio(String currency, double eurUsd, double eurGbp) {
        if(currency.equals("EUR")){
            return 1.0;
        }
        else if(currency.equals("USD")) {
            return eurUsd;
        }
        else if(currency.equals("GBP")) {
            return eurGbp;
        }
        else {
            throw new IllegalArgumentException("Currency not supported: " + currency);
        }
    }
}