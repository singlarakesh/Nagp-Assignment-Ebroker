package com.nagarro.eBroker.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.model.Trader;
import com.nagarro.eBroker.repository.EquityRepository;
import com.nagarro.eBroker.repository.TraderRepository;
import com.nagarro.eBroker.service.Implementation.EquityService;
import com.nagarro.eBroker.service.Implementation.TraderService;
import com.nagarro.eBroker.utils.BrokerUtils;

public class EquityServiceTest {
	@InjectMocks
	private EquityService equityService;

	@Mock
	private EquityRepository equityRepo;

	@Captor
	ArgumentCaptor<Integer> captor;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddEquity() {
		Equity equity = new Equity(1, 300, 1);
		Mockito.when(equityRepo.save(equity)).thenReturn(equity);
		Equity actual = equityService.addEquity(equity);
		Assertions.assertEquals(equity, actual);
	}

	@Test
	public void testGetAllEquity() {
		Equity equity = new Equity(1, 300, 1);
		List<Equity> expect = Arrays.asList(equity);
		Mockito.when(equityRepo.findAll()).thenReturn(expect);
		List<Equity> actual = equityService.getAllEquity();
		Assertions.assertEquals(expect, actual);
	}

	@Test
	public void testEquityById() {
		Equity equity = new Equity(1, 300, 1);
		Optional<Equity> equityOptional = Optional.of(equity);
		Mockito.when(equityRepo.findById(1)).thenReturn(equityOptional);
		Equity actual = equityService.getEquityById(1);
		Assertions.assertEquals(equityOptional.get(), actual);
	}

}
