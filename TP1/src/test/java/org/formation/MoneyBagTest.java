package org.formation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoneyBagTest {

	@Mock
	ConvertService convertService;
	
	@InjectMocks
	MoneyBag emptyMoneyBag = new MoneyBag();
	
	Money eur12, usd10;
	
	@Before 
	public void setUp() {
		eur12 = new Money(12, "EUR");
		usd10 = new Money(10, "USD");
	}
	
	@Test
	public void convertIntoAnExistingCurrency() {
		emptyMoneyBag.put(eur12);
		emptyMoneyBag.put(usd10);
		
		when(convertService.convert(10, "USD", "EUR")).thenReturn(100.0);
		Money result = emptyMoneyBag.convertInto("EUR");
		
		assertThat(result).extracting("amount","currency")
		.containsExactly(112.0, "EUR");
		
		verify(convertService,times(1)).convert(10, "USD", "EUR");	
	}
	
	@Test
	public void convertIntoAnNonExistingCurrency() {
		emptyMoneyBag.put(eur12);
		emptyMoneyBag.put(usd10);
		
		when(convertService.convert(12, "EUR", "YEN")).thenReturn(100.0);
		when(convertService.convert(10, "USD", "YEN")).thenReturn(100.0);
		
		Money result = emptyMoneyBag.convertInto("YEN");
		
		assertThat(result).extracting("amount","currency")
		.containsExactly(200.0,"YEN");
		
		verify(convertService,times(2)).convert(anyDouble(), anyString(), anyString());
	}
	
	@Test
	public void convertAnEmptyBagCheckAmountAndCurrency() {
		
		Money result = emptyMoneyBag.convertInto("YEN");
		
		assertThat(result).extracting("amount","currency")
		.containsExactly(0.0,"YEN");
		
		verifyNoInteractions(convertService);
	}


}
