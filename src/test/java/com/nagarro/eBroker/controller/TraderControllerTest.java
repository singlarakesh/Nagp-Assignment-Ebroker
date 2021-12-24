package com.nagarro.eBroker.controller;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.model.Trader;
import com.nagarro.eBroker.service.ITraderService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TraderController.class)
@ActiveProfiles("test")
public class TraderControllerTest {
	@MockBean
	ITraderService traderService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetTrader() throws Exception {
		Trader trader = new Trader(1, "Parusha", 100);
		List<Trader> employees = Arrays.asList(trader);

		Mockito.when(traderService.getAllTrader()).thenReturn(employees);
		mockMvc.perform(MockMvcRequestBuilders.get("/trader")).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].funds", Matchers.is(100)));
	}

	@Test
	public void testPostEquity() throws Exception {
		Trader trader = new Trader(1, "Parusha", 100);
		Mockito.when(traderService.addTrader(trader)).thenReturn(trader);
		mockMvc.perform(MockMvcRequestBuilders.post("/trader").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "        \"name\":\"Parusha\",\n" + "        \"funds\":\"100\"\n" + "}"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testAddFunds() throws Exception {
		int oldFund = 100;
		int fundToBeAdded = 50;
		Trader trader = new Trader(1, "Parusha", oldFund+fundToBeAdded);
		Mockito.when(traderService.addFunds(1, fundToBeAdded)).thenReturn(trader);
		mockMvc.perform(MockMvcRequestBuilders.put("/trader/addFunds/1/50"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.funds", Matchers.is(150)));
		;

	}

	@Test
	public void testBuyEquity() throws Exception {
		int traderInitialFund = 100;
		Equity equity = new Equity(4, 20, 1);
		Trader expectedTrader = new Trader(1, "Hemant", traderInitialFund - equity.getPrice());
		Mockito.when(traderService.buyEquity(1, 4)).thenReturn(expectedTrader);
		mockMvc.perform(MockMvcRequestBuilders.put("/trader/buyEquity/1/4"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.funds", Matchers.is(80)));
		;
	}

	@Test
	public void testSellEquity() throws Exception {
		int traderInitialFund = 80;
		Equity equity = new Equity(4, 20, 1);
		Trader expectedTrader = new Trader(1, "Hemant", traderInitialFund + equity.getPrice());
		Mockito.when(traderService.sellEquity(1, 4)).thenReturn(expectedTrader);
		mockMvc.perform(MockMvcRequestBuilders.put("/trader/sellEquity/1/4"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.funds", Matchers.is(100)));
		;
	}
}
