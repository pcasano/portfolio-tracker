package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.service.DividendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class WebsiteController {

    public WebsiteController(DividendService dividendService) {
        this.dividendService = dividendService;
    }

    private DividendService dividendService;

    @GetMapping("/")
    public String homepage(Model model, @RequestParam(required = false, defaultValue = "stranger") String username) {
        model.addAttribute("username", username);
        model.addAttribute("currentDate", new Date());
        return "dividends.html";
    }

    @GetMapping("/div-html")
    public String allTransactions(Model model) {
        System.out.println("Aqui: " + dividendService.findAll());
        model.addAttribute("mapOfDividends2018", dividendService.getDividends2018());
        model.addAttribute("mapOfDividends2019", dividendService.getDividends2019());
        model.addAttribute("mapOfDividends2020", dividendService.getDividends2020());
        model.addAttribute("mapOfDividends2021", dividendService.getDividends2021());
        model.addAttribute("mapOfDividends2022", dividendService.getDividends2022());
        model.addAttribute("mapOfDividends2023", dividendService.getDividends2023());
        model.addAttribute("dividends2018", dividendService.findAll().stream().filter(div -> div.getYear().equals("2018")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends2019", dividendService.findAll().stream().filter(div -> div.getYear().equals("2019")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends2020", dividendService.findAll().stream().filter(div -> div.getYear().equals("2020")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends2021", dividendService.findAll().stream().filter(div -> div.getYear().equals("2021")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends2022", dividendService.findAll().stream().filter(div -> div.getYear().equals("2022")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends2023", dividendService.findAll().stream().filter(div -> div.getYear().equals("2023")).mapToDouble(Dividend::getAmount).sum());
        model.addAttribute("dividends", dividendService.findAll());
        return "index.html";
    }
}