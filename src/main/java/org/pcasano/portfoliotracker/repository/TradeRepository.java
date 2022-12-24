package org.pcasano.portfoliotracker.repository;

import org.pcasano.portfoliotracker.model.Trade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends CrudRepository<Trade, String> {

}
