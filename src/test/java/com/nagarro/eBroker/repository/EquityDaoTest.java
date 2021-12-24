package com.nagarro.eBroker.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.eBroker.model.Equity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class EquityDaoTest {

	@Autowired
	EquityRepository equityRepo;

	@Test
	public void testCRUDEquity() {
		Equity equity = new Equity(4, 100, 1);
		equityRepo.save(equity);
		List<Equity> equityList = equityRepo.findAll();
		Assertions.assertThat(equityList).extracting((emp) -> emp.getPrice()).containsOnly(100);
	}
}
