package org.pcasano.portfoliotracker.web;

import org.pcasano.portfoliotracker.dto.CompanyDto;
import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.model.Company;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.service.CompanyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create-company")
    public Company createCompany(@RequestBody CompanyDto companyDto) throws ParseException {
        return companyService.create(companyDto);
    }

    @PostMapping("/create-companies")
    public List<Company> createDividends(@RequestBody List<CompanyDto> listOfCompanyDto) throws ParseException {
        return companyService.create(listOfCompanyDto);
    }
}
