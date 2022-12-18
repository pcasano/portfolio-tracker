package org.pcasano.portfoliotracker.web;

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
        model.addAttribute("dividends", dividendService.findAll());
        return "index.html";
    }
}