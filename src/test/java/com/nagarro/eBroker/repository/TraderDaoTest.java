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
import com.nagarro.eBroker.model.Trader;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class TraderDaoTest {
	@Autowired
	TraderRepository traderRepo;

	@Test
	public void testTrader() {
		Trader trader = new Trader(1, "Hemant", 100);
		traderRepo.save(trader);

		List<Trader> traderList = traderRepo.findAll();
		Assertions.assertThat(traderList).extracting((trad) -> trad.getFunds()).containsOnly(100);
	}
}
