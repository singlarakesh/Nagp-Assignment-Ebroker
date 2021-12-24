package com.nagarro.eBroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.eBroker.model.Equity;

@Repository
public interface EquityRepository extends JpaRepository<Equity, Integer> {

}
