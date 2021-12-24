package com.nagarro.eBroker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.eBroker.model.Trader;
import com.nagarro.eBroker.service.ITraderService;

@RestController
@RequestMapping(value = "/trader")
public class TraderController {
	@Autowired
	ITraderService traderService;

	@PostMapping
	public Trader addTrader(@RequestBody Trader trader) {
		return traderService.addTrader(trader);
	}

	@GetMapping
	public List<Trader> getTrader() {
		return traderService.getAllTrader();
	}

	@PutMapping("/buyEquity/{traderId}/{equityId}")
	public Trader buyEquity(@PathVariable("traderId") long traderId, @PathVariable("equityId") int equityId) {
		return traderService.buyEquity(traderId, equityId);
	}

	@PutMapping("/sellEquity/{traderId}/{equityId}")
	public Trader sellEquity(@PathVariable("traderId") long traderId, @PathVariable("equityId") int equityId) {
		return traderService.sellEquity(traderId, equityId);
	}

	@PutMapping("/addFunds/{traderId}/{fundAmount}")
	public Trader addFunds(@PathVariable("traderId") long traderId, @PathVariable("fundAmount") int fundAmount) {
		return traderService.addFunds(traderId, fundAmount);
	}

}
