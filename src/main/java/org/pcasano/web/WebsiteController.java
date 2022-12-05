package org.pcasano.web;

import org.pcasano.model.Dividend;
import org.pcasano.service.DividendService;
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

    @GetMapping("/div_html")
    public String allTransactions(Model model) {
        System.out.println("Aqui: " + dividendService.findAll());
        model.addAttribute("dividends", dividendService.findAll());
        model.addAttribute("total_dividends", dividendService.findAll().stream().mapToDouble(Dividend::getAmount).sum());
        return "dividends.html";
    }
}
