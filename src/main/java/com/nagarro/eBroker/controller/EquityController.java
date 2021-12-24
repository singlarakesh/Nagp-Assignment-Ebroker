package com.nagarro.eBroker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.eBroker.model.Equity;
import com.nagarro.eBroker.service.IEquityService;

@RestController
@RequestMapping(value = "/equity")
public class EquityController {
	@Autowired
	IEquityService equityService;

	@PostMapping
	public Equity addEquity(@RequestBody Equity equity) {
		return equityService.addEquity(equity);
	}

	@GetMapping
	public List<Equity> getEquity() {
		return equityService.getAllEquity();
	}
	
}
