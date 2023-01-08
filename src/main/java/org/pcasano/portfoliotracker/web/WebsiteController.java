package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.model.Currency;
import org.pcasano.portfoliotracker.model.Portfolio;
import org.pcasano.portfoliotracker.model.Trade;
import org.pcasano.portfoliotracker.model.Trend;
import org.pcasano.portfoliotracker.service.CompanyService;
import org.pcasano.portfoliotracker.service.CurrencyService;
import org.pcasano.portfoliotracker.service.DividendService;
import org.pcasano.portfoliotracker.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class WebsiteController {

    public WebsiteController(DividendService dividendService, TradeService tradeService, CompanyService companyService, CurrencyService currencyService) {
        this.dividendService = dividendService;
        this.tradeService = tradeService;
        this.companyService = companyService;
        this.currencyService = currencyService;

    }

    private DividendService dividendService;
    private TradeService tradeService;
    private CompanyService companyService;
    private CurrencyService currencyService;

    private Double eurUsdRatio = null;
    private Double eurGbpRatio = null;
    private Map<String, StockQuote> mapOfMarketQuotes = new HashMap<>();



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

    @GetMapping("/currency-html")
    public String getCurrencyPage(Model model) throws IOException {
        List<Trade> tradeList = tradeService.findAll();
        List<Portfolio> listOfPortfolio = new ArrayList<>();
        Set<String> setOfSymbols = new HashSet<>(tradeList.stream().map(Trade::getSymbol).toList());
        Set<String> setOfCountries = new HashSet<>(tradeList.stream().map(Trade::getCountry).toList());
        Set<String> setOfCurrencies = new HashSet<>(tradeList.stream().map(Trade::getCurrency).toList());

        for(String symbol:setOfSymbols) {
            int quantity = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).mapToInt(Trade::getQuantity).sum();
            if (quantity > 0) {
                List<Trade> filteredTradeList = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).toList();
                String currency = filteredTradeList.stream().map(Trade::getCurrency).findFirst().orElse("N/A");
                String country = filteredTradeList.stream().map(Trade::getCountry).findFirst().orElse("N/A");
                double marketPriceOriginalCurrency = currency.equals("GBP") ?
                        getCompanyQuote(symbol).getPrice().doubleValue() / 100 : getCompanyQuote(symbol).getPrice().doubleValue();
                listOfPortfolio.add(new Portfolio(
                        symbol,
                        country,
                        currency,
                        quantity,
                        marketPriceOriginalCurrency / this.getCurrentRatio(currency, getEurUsdRatio(), getEurGbpRatio())
                ));
            }
        }
        List<List<Object>> listOfCountries = new ArrayList<>();
        setOfCountries.forEach(country -> {
            listOfCountries.add(
                    List.of(
                            country,
                            listOfPortfolio.stream().filter(portfolio -> portfolio.getCountry().equals(country)).mapToDouble(portfolio -> portfolio.getQuantity() * portfolio.getMarketPriceBaseCurrency()).sum())
            );
        });
        List<List<Object>> listOfCurrencies = new ArrayList<>();
        double portfolioMarketValueBaseCurrency = listOfPortfolio.stream().mapToDouble(portfolio -> portfolio.getQuantity() * portfolio.getMarketPriceBaseCurrency()).sum();
        setOfCurrencies.forEach(currency -> {
            double marketValueBaseCurrencyGivenCurrency = listOfPortfolio.stream().filter(portfolio -> portfolio.getCurrency().equals(currency)).mapToDouble(portfolio -> portfolio.getQuantity() * portfolio.getMarketPriceBaseCurrency()).sum();
            listOfCurrencies.add(
                    List.of(currency,
                            100 * (1 - (portfolioMarketValueBaseCurrency- marketValueBaseCurrencyGivenCurrency) / portfolioMarketValueBaseCurrency))
            );
        });
        List<Currency> listOfFxOperations = currencyService.findAll();

        double myUsdRate = getMyRateGivenCurrency(listOfFxOperations, "USD");
        double myGbpRate = getMyRateGivenCurrency(listOfFxOperations, "GBP");

        model.addAttribute("listOfCountries", listOfCountries);
        model.addAttribute("listOfCurrencies", listOfCurrencies);
        model.addAttribute("currencies", listOfFxOperations);
        model.addAttribute("myUsdRatio", myUsdRate);
        model.addAttribute("myGbpRatio", myGbpRate);
        model.addAttribute("currentUsdRatio", eurUsdRatio);
        model.addAttribute("currentGbpRatio", eurGbpRatio);
            return "currencyPage.html";
    }

    @GetMapping("/portfolio-html")
    public String getPortfolioPage(Model model) throws IOException {
        List<Portfolio> portfolioList = new ArrayList<>();
        List<Trade> tradeList = tradeService.findAll();
        List<List<Object>> listOfMarketValuePositionsBaseCurrency = new ArrayList<>();
        Set<String> setOfSymbols = new HashSet<>(tradeList.stream().map(Trade::getSymbol).toList());
        double eurUsdRatio = getEurUsdRatio();
        double eurGbpRatio = getEurGbpRatio();

        setOfSymbols.forEach(symbol -> {
            int quantity = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).mapToInt(Trade::getQuantity).sum();
            if (quantity > 0) {
                List<Trade> filteredTradeList = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).toList();
                try {
                    String currency = filteredTradeList.stream().map(Trade::getCurrency).findFirst().orElse("N/A");
                    double marketPriceOriginalCurrency = currency.equals("GBP") ?
                            getCompanyQuote(symbol).getPrice().doubleValue() / 100 : getCompanyQuote(symbol).getPrice().doubleValue();

                    double openPriceOriginalCurrency = filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceOriginalCurrency()).sum() / quantity;
                    double openValueBaseCurrency = filteredTradeList.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPriceBaseCurrency()).sum();
                    double marketValueBaseCurrency = quantity * marketPriceOriginalCurrency / this.getCurrentRatio(currency, eurUsdRatio, eurGbpRatio);
/*                    double marketValueBaseCurrency = filteredTradeList
                            .stream().
                            mapToDouble(trade -> trade.getQuantity() * marketPriceOriginalCurrency / this.getCurrentRatio(currency, eurUsdRatio, eurGbpRatio)).sum();*/
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
                            marketValueBaseCurrency - openValueBaseCurrency,
                            openValueBaseCurrency
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

    @GetMapping("/trends-html")
    public String getTrendsPage(Model model) throws IOException {
        List<Trade> tradeList = tradeService.findAll();
        Set<String> setOfSymbols = new HashSet<>(tradeList.stream().map(Trade::getSymbol).toList());
        List<Trend> listOfTrends = new ArrayList<>();

        for (String symbol : setOfSymbols) {
            int quantity = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).mapToInt(Trade::getQuantity).sum();
            if(quantity > 0) {
                List<Trade> filteredTradeList = tradeList.stream().filter(trade -> trade.getSymbol().equals(symbol)).toList();
                String currency = filteredTradeList.stream().map(Trade::getCurrency).findFirst().orElse("N/A");
                listOfTrends.add(
                        new Trend(
                                symbol,
                                currency.equals("GBP") ? getCompanyQuote(symbol).getPrice().doubleValue() / 100 : getCompanyQuote(symbol).getPrice().doubleValue(),
                                currency.equals("GBP") ? getCompanyQuote(symbol).getPriceAvg50().doubleValue() / 100 : getCompanyQuote(symbol).getPriceAvg50().doubleValue(),
                                currency.equals("GBP") ? getCompanyQuote(symbol).getPriceAvg200().doubleValue() / 100 : getCompanyQuote(symbol).getPriceAvg200().doubleValue(),
                                getCompanyQuote(symbol).getChangeFromAvg50InPercent().doubleValue(),
                                getCompanyQuote(symbol).getChangeFromAvg200InPercent().doubleValue()
                        ));
            }
        }
        List<Trend> listTrendsSorted50Avg = new ArrayList<>(listOfTrends);
        List<Trend> listTrendsSorted200Avg = new ArrayList<>(listOfTrends);
        listTrendsSorted50Avg.sort(Comparator.comparingDouble(Trend::getChangeInPrice50Avg));
        listTrendsSorted200Avg.sort(Comparator.comparingDouble(Trend::getChangeInPrice200Avg));

        for(int i=1; i<listTrendsSorted50Avg.size() + 1; i++) {
            listTrendsSorted50Avg.get(i - 1).setNr(i);
        }
        for(int i=1; i<listTrendsSorted200Avg.size() + 1; i++) {
            listTrendsSorted200Avg.get(i - 1).setNr(i);
        }

        model.addAttribute("listOfTrends50Avg", listTrendsSorted50Avg.stream().filter(trend -> trend.getNr() < 16).toList());
        model.addAttribute("listOfTrends200Avg", listTrendsSorted200Avg.stream().filter(trend -> trend.getNr() < 16).toList());
        return "trendsPage.html";
    }

    private String getYahooSymbol(String symbol) {
        return companyService.findAll().stream().filter(company -> company.getSymbol().equals(symbol)).findFirst().get().getYahooSymbol();
    }

    private StockQuote getCompanyQuote(String symbol) throws IOException {
        Optional<StockQuote> op = Optional.ofNullable(mapOfMarketQuotes.get(symbol));
        if(op.isPresent()) {
            return op.get();
        }
        else {
            StockQuote quote = YahooFinance.get(getYahooSymbol(symbol)).getQuote();
            mapOfMarketQuotes.put(symbol, quote);
            return quote;
        }
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

    private Double getEurUsdRatio() throws IOException {
        if (Optional.ofNullable(eurUsdRatio).isEmpty()) {
            eurUsdRatio = YahooFinance.get("EURUSD=X").getQuote().getPrice().doubleValue();
        }
        return eurUsdRatio;
    }

    private Double getEurGbpRatio() throws IOException {
        if (Optional.ofNullable(eurGbpRatio).isEmpty()) {
            eurGbpRatio = YahooFinance.get("EURGBP=X").getQuote().getPrice().doubleValue();
        }
        return eurGbpRatio;
    }

    private double getMyRateGivenCurrency(List<Currency> listOfFxOperations, String currency) {
        double totalOperationsConverted = listOfFxOperations.stream().filter(fx -> fx.getTargetCurrency().equals(currency)).mapToDouble(fx -> fx.getAmount() * fx.getRate()).sum();
        double totalOperations = listOfFxOperations.stream().filter(fx -> fx.getTargetCurrency().equals(currency)).mapToDouble(Currency::getAmount).sum();
        return totalOperationsConverted / totalOperations;
    }
}