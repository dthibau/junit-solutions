package org.formation;

import static org.junit.Assert.*;


import org.junit.Test;

public class MoneyTest {

	Money eur12 = new Money(12,"EUR");
	Money eur14 = new Money(14,"EUR");
	Money usd10 = new Money(10,"USD");
	
	

	@Test
	public void whenAddWithSameCurrencyCheckSum() {
	
		Money result = eur12.add(eur14);
		assertEquals("Check amount",26,result.getAmount(),0);
		assertEquals("Check currency","EUR",result.getCurrency());
	}

	@Test
	public void whenAddWithNullCheckUnchanged() {
		Money result = eur12.add(null);
		assertEquals("Check amount",12,result.getAmount(),0);
		assertEquals("Check currency","EUR",result.getCurrency());
	}
	
	@Test
	public void whenAddWithDifferentCurrencyCheckIllegalArgumentException() {
		
		try {
			eur12.add(usd10);
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {}
		
		
	}

}
