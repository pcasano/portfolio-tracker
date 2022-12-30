package org.pcasano.portfoliotracker.repository;

import org.pcasano.portfoliotracker.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

}
