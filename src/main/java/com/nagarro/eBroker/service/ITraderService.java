package com.nagarro.eBroker.service;

import java.util.List;

import com.nagarro.eBroker.model.Trader;

public interface ITraderService {

	Trader addTrader(Trader trader);

	List<Trader> getAllTrader();

	Trader buyEquity(long traderId, int equityId);

	Trader sellEquity(long traderId, int equityId);

	Trader addFunds(long traderId, int funds);
}
