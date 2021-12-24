package com.nagarro.eBroker.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nagarro.eBroker.handler.NotInServiceException;
import com.nagarro.eBroker.handler.RecordNotFoundException;
import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.model.Trader;
import com.nagarro.eBroker.repository.TraderRepository;
import com.nagarro.eBroker.service.Implementation.EquityService;
import com.nagarro.eBroker.service.Implementation.TraderService;
import com.nagarro.eBroker.utils.BrokerUtils;

public class TraderServiceTest {
	@InjectMocks
	private TraderService traderService;

	@Mock
	private TraderRepository traderRepo;

	@Mock
	private EquityService equityService;

	@Captor
	ArgumentCaptor<Integer> captor;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddTrader() {
		Trader trader = new Trader(1, "Hemant", 100);
		Mockito.when(traderRepo.save(trader)).thenReturn(trader);
		Trader actual = traderService.addTrader(trader);
		Assertions.assertEquals(trader, actual);
	}

	@Test
	public void testGetAllTrader() {
		Trader trader = new Trader(1, "Hemant", 100);
		List<Trader> expect = Arrays.asList(trader);
		Mockito.when(traderRepo.findAll()).thenReturn(expect);
		List<Trader> actual = traderService.getAllTrader();
		Assertions.assertEquals(expect, actual);
	}

	@DisplayName("Test when equity quantity not present")
	@Test
	public void testBuyEquityForLowQuantity() {
		try (MockedStatic<BrokerUtils> utilites = Mockito.mockStatic(BrokerUtils.class)) {
			utilites.when(BrokerUtils::timeAndDayCheck).thenReturn(true);
			Trader trader = new Trader(1, "Hemant", 100);
			Optional<Trader> traderOptional = Optional.of(trader);
			Mockito.when(traderRepo.findById((long) 1)).thenReturn(traderOptional);
			Equity equity = new Equity(4, 100, 0);
			Mockito.when(equityService.getEquityById(4)).thenReturn(equity);
			Assertions.assertThrows(RecordNotFoundException.class, () -> traderService.buyEquity(1, 4));
		}
	}

	@DisplayName("Test when fund are low")
	@Test
	public void testBuyEquityForInSuffFund() {
		try (MockedStatic<BrokerUtils> utilites = Mockito.mockStatic(BrokerUtils.class)) {
			utilites.when(BrokerUtils::timeAndDayCheck).thenReturn(true);
			Trader trader = new Trader(1, "Hemant", 20);
			Optional<Trader> traderOptional = Optional.of(trader);
			Mockito.when(traderRepo.findById((long) 1)).thenReturn(traderOptional);
			Equity equity = new Equity(4, 100, 1);
			Mockito.when(equityService.getEquityById(4)).thenReturn(equity);
			Assertions.assertThrows(RecordNotFoundException.class, () -> traderService.buyEquity((long) 1, 4));
		}
	}

	@DisplayName("Buy Equity")
	@Test
	public void testBuyEquity() {
		try (MockedStatic<BrokerUtils> utilites = Mockito.mockStatic(BrokerUtils.class)) {
			utilites.when(BrokerUtils::timeAndDayCheck).thenReturn(true);
			Trader trader = new Trader(1, "Hemant", 120);
			Optional<Trader> traderOptional = Optional.of(trader);
			Mockito.when(traderRepo.findById((long) 1)).thenReturn(traderOptional);
			Equity equity = new Equity(4, 100, 1);
			Mockito.when(equityService.getEquityById(4)).thenReturn(equity);

			Trader expectedTrader = new Trader(1, "Hemant", 20);
			expectedTrader.getEquityList().add(equity);
			Mockito.when(traderRepo.save(trader)).thenReturn(expectedTrader);
			Trader actualTrader = traderService.buyEquity(1, 4);
			Assertions.assertEquals(expectedTrader, actualTrader);

		}
	}

	@DisplayName("Sell Equity")
	@Test
	public void testSellEquity() {
		try (MockedStatic<BrokerUtils> utilites = Mockito.mockStatic(BrokerUtils.class)) {
			utilites.when(BrokerUtils::timeAndDayCheck).thenReturn(true);
			Equity equity = new Equity(4, 20, 1);
			Mockito.when(equityService.getEquityById(4)).thenReturn(equity);
			Trader trader = new Trader(1, "Hemant", 80);
			trader.getEquityList().add(equity);
			equity.getTrader().add(trader);
			Optional<Trader> traderOptional = Optional.of(trader);
			Mockito.when(traderRepo.findById((long) 1)).thenReturn(traderOptional);

			Trader expectedTrader = new Trader(1, "Hemant", 100);
			Mockito.when(traderRepo.save(trader)).thenReturn(expectedTrader);
			Trader actualTrader = traderService.sellEquity(1, 4);
			Assertions.assertEquals(expectedTrader, actualTrader);
//			Assertions.assertEquals(NullPointerException.class, traderService.sellEquity(1, 5));
		}
	}

	@DisplayName("Service time Check")
	@Test
	public void testServiceTimeCheck() {
		try (MockedStatic<BrokerUtils> utilites = Mockito.mockStatic(BrokerUtils.class)) {
			utilites.when(BrokerUtils::timeAndDayCheck).thenReturn(false);
			Assertions.assertThrows(NotInServiceException.class, () -> traderService.buyEquity((long) 1, 4));
			Assertions.assertThrows(NotInServiceException.class, () -> traderService.sellEquity((long) 1, 4));
		}
	}

	@DisplayName("static method check")
	@Test
	public void testStaticMethdBranching() {
		LocalDate today = LocalDate.parse("2021-12-25");
		Assertions.assertEquals(false, BrokerUtils.isWeekday(today));
		today = LocalDate.parse("2021-12-26");
		Assertions.assertEquals(false, BrokerUtils.isWeekday(today));
	}

	@DisplayName("Test Fund")
	@Test
	public void testAddFund() {
		int prevFnd = 20;
		int fund = 100;
		Trader trader = new Trader(1, "Hemant", prevFnd);
		Optional<Trader> traderOptional = Optional.of(trader);
		Mockito.when(traderRepo.findById((long) 1)).thenReturn(traderOptional);
		traderService.addFunds(1, fund);
		Assertions.assertEquals(trader.getFunds(), prevFnd + fund);
	}

	@DisplayName("Test for trader id not found")
	@Test
	public void testForTraderNotFound() {
		Mockito.when(traderRepo.findById((long) 1)).thenReturn(Optional.empty());
		Assertions.assertThrows(RecordNotFoundException.class, () -> traderService.addFunds(1, 4));

		Assertions.assertThrows(RecordNotFoundException.class, () -> traderService.buyEquity(1, 4));
	}

}
