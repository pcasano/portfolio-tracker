package org.pcasano.web;

import org.pcasano.dto.DividendDto;
import org.pcasano.model.Dividend;
import org.pcasano.service.DividendService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        public Dividend createDividend(@RequestBody @Valid DividendDto dividendDto) {
            return dividendService.create(
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
    public List<Dividend> createDividends(@RequestBody @Valid List<DividendDto> listOfDividendDto) {
        return dividendService.create(listOfDividendDto);
    }
}
