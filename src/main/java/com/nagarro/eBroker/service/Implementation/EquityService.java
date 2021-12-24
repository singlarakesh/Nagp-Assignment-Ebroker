package com.nagarro.eBroker.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.repository.EquityRepository;
import com.nagarro.eBroker.service.IEquityService;

@Service
public class EquityService implements IEquityService {
	@Autowired
	EquityRepository equityRepo;

	@Override
	public Equity addEquity(Equity equity) {
		return equityRepo.save(equity);
	}

	@Override
	public List<Equity> getAllEquity() {
		// TODO Auto-generated method stub
		return equityRepo.findAll();
	}

	@Override
	public Equity getEquityById(int id) {
		// TODO Auto-generated method stub
		Optional<Equity> equity = equityRepo.findById(id);
		return equity.get();
	}

}
