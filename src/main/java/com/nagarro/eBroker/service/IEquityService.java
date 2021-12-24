package com.nagarro.eBroker.service;

import java.util.List;

import com.nagarro.eBroker.model.Equity;

public interface IEquityService {

	Equity addEquity(Equity equity);

	List<Equity> getAllEquity();

	Equity getEquityById(int id);

}
