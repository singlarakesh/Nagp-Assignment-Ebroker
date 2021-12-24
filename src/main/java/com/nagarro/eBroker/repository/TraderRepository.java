package com.nagarro.eBroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.eBroker.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {

}
