package org.formation;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class MoneyParameterizedTest {
	
	private MoneyBag moneyBag;
	private String targetCurrency;
	private Money expectedResult;
	ConvertService mockService;
	
	@Parameters
	public static Collection<Object[]> data() {  
		MoneyBag emptyMoneyBag = new MoneyBag();
		MoneyBag mbEur10Usd10 = new MoneyBag(new Money(10,"EUR"),new Money(10,"USD"));
		
		return Arrays.asList(
				new Object[][] {
					{ emptyMoneyBag, "YEN", new Money(0,"YEN") }, 
					{ mbEur10Usd10, "EUR", new Money(110, "EUR") }, 
					{ mbEur10Usd10, "YEN", new Money(200, "YEN")  }
				});
	}
	
	public MoneyParameterizedTest(MoneyBag moneyBag, String targetCurrency, Money expectedResult) {
		super();
		this.moneyBag = moneyBag;
		this.targetCurrency = targetCurrency;
		this.expectedResult = expectedResult;
	}
	
	@Before
	public void setUp() {
		mockService = Mockito.mock(ConvertService.class);
		
		when(mockService.convert(anyDouble(), anyString(), anyString())).thenReturn(100.0);
	}
	
	@Test
	public void test() {
		
		moneyBag.setConvertService(mockService);
		
		Money result = moneyBag.convertInto(targetCurrency);
		
		assertThat(result).extracting("amount","currency")
		.containsExactly(expectedResult.getAmount(),expectedResult.getCurrency());
		
	}


}
