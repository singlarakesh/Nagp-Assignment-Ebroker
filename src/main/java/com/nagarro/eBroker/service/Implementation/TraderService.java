package com.nagarro.eBroker.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.eBroker.handler.NotInServiceException;
import com.nagarro.eBroker.handler.RecordNotFoundException;
import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.model.Trader;
import com.nagarro.eBroker.repository.TraderRepository;
import com.nagarro.eBroker.service.IEquityService;
import com.nagarro.eBroker.service.ITraderService;
import com.nagarro.eBroker.utils.BrokerUtils;

@Service
public class TraderService implements ITraderService {
	@Autowired
	TraderRepository traderRepo;
	@Autowired
	IEquityService equityService;

	@Override
	public Trader addTrader(Trader trader) {
		// TODO Auto-generated method stub
		return traderRepo.save(trader);
	}

	@Override
	public List<Trader> getAllTrader() {
		// TODO Auto-generated method stub
		return traderRepo.findAll();
	}

	@Override
	public Trader buyEquity(long traderId, int equityId) {
		if (this.getServiceAvailability()) {
			throw new NotInServiceException("Service available from 9-5 mon-friday");
		}
		Equity equity = equityService.getEquityById(equityId);
		Optional<Trader> traderOptional = traderRepo.findById(traderId);
		if (traderOptional.isPresent()) {
			Trader trader = traderOptional.get();
			int quantity = equity.getQuantity();
			if (quantity <= 0) {
				throw new RecordNotFoundException("Equity Not available To buy");
			}
			int pricePerQuantity = equity.getPrice();
			if (pricePerQuantity < trader.getFunds()) {
				return this.saveEquityForTrader(equity, trader, quantity, pricePerQuantity);
			} else {
				throw new RecordNotFoundException("Not Suffiecient fund");
			}
		} else {
			throw new RecordNotFoundException("No trader record exist for given id");
		}
	}

	public Trader saveEquityForTrader(Equity equity, Trader trader, int quantity, int pricePerQuantity) {
		// TODO Auto-generated method stub
		equity.setQuantity(quantity - 1);
		trader.setFunds(trader.getFunds() - pricePerQuantity);
		trader.getEquityList().add(equity);
		equity.getTrader().add(trader);
		return traderRepo.save(trader);
	}

	public Trader sellEquity(long traderId, int equityId) {
		if (this.getServiceAvailability()) {
			throw new NotInServiceException("Service available from 9-5 mon-friday");
		}
		Trader trader = traderRepo.findById(traderId).get();
		trader.setFunds(trader.getFunds() + equityService.getEquityById(equityId).getPrice());
		trader.getEquityList().removeIf(equity -> equity.getId() == equityId);
		Equity equity = equityService.getEquityById(equityId);
		equity.getTrader().removeIf(traderObj -> traderObj.getId() == traderId);
		traderRepo.save(trader);
		return trader;
	}

	@Override
	public Trader addFunds(long traderId, int funds) {
		// TODO Auto-generated method stub
		Optional<Trader> traderOptional = traderRepo.findById(traderId);
		if (traderOptional.isPresent()) {
			Trader trader = traderOptional.get();
			trader.setFunds(funds + trader.getFunds());
			return traderRepo.save(trader);
		} else {
			throw new RecordNotFoundException("No trader record exist for given id");
		}
	}

	public boolean getServiceAvailability() {
		return !BrokerUtils.timeAndDayCheck();
	}

}
