package org.pcasano.portfoliotracker.repository;

import org.pcasano.portfoliotracker.model.Company;
import org.pcasano.portfoliotracker.model.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {

}
