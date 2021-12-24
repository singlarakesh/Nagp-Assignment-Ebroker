package com.nagarro.eBroker.controller;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
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
import com.nagarro.eBroker.service.IEquityService;
import com.nagarro.eBroker.utils.BrokerUtils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EquityController.class)
@ActiveProfiles("test")
public class EquityControllerTest {
	@MockBean
	IEquityService equityService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetEquity() throws Exception {
		Equity equity = new Equity(1, 300, 1);
		List<Equity> employees = Arrays.asList(equity);

		Mockito.when(equityService.getAllEquity()).thenReturn(employees);
		mockMvc.perform(MockMvcRequestBuilders.get("/equity")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(300)));
	}

	@Test
	public void testPostEquity() throws Exception {
		Equity equity = new Equity(1, 300, 2);
		Mockito.when(equityService.addEquity(equity)).thenReturn(equity);
		mockMvc.perform(MockMvcRequestBuilders.post("/equity").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "        \"price\":\"300\",\n" + "        \"quantity\":\"2\"\n" + "}"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
		;

	}

}
