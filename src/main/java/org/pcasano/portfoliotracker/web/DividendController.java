package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.service.DividendService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class DividendController {


    private DividendService dividendService;

    public DividendController(DividendService dividendService) {
        this.dividendService = dividendService;
    }

    @GetMapping("/dividends")
    @ResponseBody
    public List<Dividend> dividends() {
        return dividendService.findAll();
    }

    @PostMapping("/create-dividend")
    public Dividend createDividend(@RequestBody DividendDto dividendDto) throws ParseException {
        return dividendService.create(
                dividendDto.getId(),
                dividendDto.getPaymentDate(),
                dividendDto.getCompanyName(),
                dividendDto.getAmount(),
                dividendDto.getTax(),
                dividendDto.getCurrency(),
                dividendDto.getRate(),
                dividendDto.getActivityCode()
        );
    }

    @PostMapping("/create-dividends")
    public List<Dividend> createDividends(@RequestBody List<DividendDto> listOfDividendDto) throws ParseException {
        return dividendService.create(listOfDividendDto);
    }
}