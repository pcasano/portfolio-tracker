package org.pcasano.portfoliotracker.service;

import org.pcasano.portfoliotracker.dto.CompanyDto;
import org.pcasano.portfoliotracker.dto.DividendDto;
import org.pcasano.portfoliotracker.model.Company;
import org.pcasano.portfoliotracker.model.Dividend;
import org.pcasano.portfoliotracker.repository.CompanyRepository;
import org.pcasano.portfoliotracker.repository.DividendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> findAll() {
        Iterator<Company> companyIterator = companyRepository.findAll().iterator();
        List<Company> companyList = new ArrayList<>();
        while (companyIterator.hasNext())
            companyList.add(companyIterator.next());
        return companyList;
    }


    public Company create(CompanyDto companyDto) {
        return new Company(
                companyDto.getSymbol(),
                companyDto.getDescription(),
                companyDto.getYahooSymbol()
        );
    }

    public List<Company> create(List<CompanyDto> listOfCompanyDto) throws ParseException {
        List<Company> listOfCompanies = new ArrayList<>();
        for (CompanyDto companyDto : listOfCompanyDto) {
            listOfCompanies.add(
                    new Company(
                            companyDto.getSymbol(),
                            companyDto.getDescription(),
                            companyDto.getYahooSymbol()));
        }
        companyRepository.saveAll(listOfCompanies);
        return listOfCompanies;
    }
}
