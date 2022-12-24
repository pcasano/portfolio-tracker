package org.pcasano.portfoliotracker.repository;

import org.pcasano.portfoliotracker.model.Dividend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends CrudRepository<Dividend, Integer> {

}