package org.formation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IMoneyTest {

	Money eur12, eur10, usd10,yen10;
	MoneyBag mbEur12Usd10,mbEur12Yen10,emptyMoneyBag;
	
	@Before
	public void setUp() {
		eur12 = new Money(12,"EUR");
		eur10 = new Money(10,"EUR");
		usd10 = new Money(10,"USD");
		yen10 = new Money(10,"YEN");
		
		mbEur12Usd10 = new MoneyBag();
		mbEur12Usd10.put(eur12);
		mbEur12Usd10.put(usd10);

		mbEur12Yen10 = new MoneyBag();
		mbEur12Yen10.put(eur12);
		mbEur12Yen10.put(yen10);
		
		emptyMoneyBag = new MoneyBag();

	}
	
	@Test
	public void add2MoneyWithSameCurrencyThenMoney() {
		IMoney result = eur12.add(eur10);
		
		assertThat(result).isExactlyInstanceOf(Money.class)
		               .extracting(m -> ((Money)m).getAmount())
		               .isEqualTo(22.0);
		
	}
	
	@Test
	public void add2MoneyWithDifferentCurrencyThenMoneyBag() {
		IMoney result = eur12.add(usd10);
		
		assertThat(result)
	    .as("Check IMoney Type and result")
	    .isInstanceOf(MoneyBag.class)
	    .extracting(
	        r -> ((MoneyBag) r).get("EUR"),
	        r -> ((MoneyBag) r).get("USD"),
	        r -> List.copyOf(((MoneyBag) r).getCurrencies())
	    )
	    .usingRecursiveComparison()   // <--- comparaison sur le contenu
	    .isEqualTo(Arrays.asList(
	        12.0,
	        10.0,
	        Arrays.asList("EUR", "USD")
	    ));

	}
	
	@Test
	public void addMoneyWithMoneyBagNotContainingCurrencyThenCheckNewCurrency() {
		IMoney result = yen10.add(mbEur12Usd10);
		
		assertThat(result)
	    .as("Check IMoney Type and result")
	    .isInstanceOf(MoneyBag.class)
	    .extracting(
	        r -> ((MoneyBag) r).get("EUR"),
	        r -> ((MoneyBag) r).get("USD"),
	        r -> ((MoneyBag) r).get("YEN"),
	        r -> ((MoneyBag) r).getCurrencies().stream().sorted().toList()
	    )
	    .usingRecursiveComparison() // <--- comparaison sur le contenu
	    .ignoringCollectionOrder()
	    .isEqualTo(Arrays.asList(
	        12.0,
	        10.0,
	        10.0,
	        Arrays.asList("EUR", "USD","YEN")
	    ));
		
		assertTrue("add2MoneyWithSameCurrencyThenMoney",result instanceof MoneyBag);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",12,((MoneyBag)result).get("EUR"),0);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("USD"),0);	
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("YEN"),0);
		
		result = mbEur12Usd10.add(yen10);
			
		assertTrue("add2MoneyWithSameCurrencyThenMoney",result instanceof MoneyBag);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",12,((MoneyBag)result).get("EUR"),0);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("USD"),0);	
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("YEN"),0);

		
	}
	@Test
	public void addMoneyWithMoneyBagContainingCurrencyThenCheckSum() {
		
		IMoney result = eur10.add(mbEur12Usd10);
		
		assertTrue("add2MoneyWithSameCurrencyThenMoney",result instanceof MoneyBag);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",22,((MoneyBag)result).get("EUR"),0);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("USD"),0);	
		
		result = mbEur12Usd10.add(eur10);;
		
		assertTrue("add2MoneyWithSameCurrencyThenMoney",result instanceof MoneyBag);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",22,((MoneyBag)result).get("EUR"),0);
		assertEquals("add2MoneyWithDifferentCurrencyThenMoneyBag - Check Amount",10,((MoneyBag)result).get("USD"),0);	
		
	}

	@Test
	public void add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency() {
		
		IMoney result = mbEur12Usd10.add(mbEur12Yen10); 
		assertTrue("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency",result instanceof MoneyBag);
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",24,((MoneyBag)result).get("EUR"),0);
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",10,((MoneyBag)result).get("USD"),0);	
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",10,((MoneyBag)result).get("YEN"),0);
		
	}
	@Test
	public void addMoneyWithEmptyMoneyBagOrNullShouldReturnMoney() {
		
		IMoney result = eur12.add(emptyMoneyBag);
		assertTrue("addMoneyWithEmptyMoneyBagOrNullShouldReturnMoney",result instanceof Money);
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",12,((Money)result).getAmount(),0);	
		
		result = eur12.add(null);
		assertTrue("addMoneyWithEmptyMoneyBagOrNullShouldReturnMoney",result instanceof Money);
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",12,((Money)result).getAmount(),0);	
		
		result = emptyMoneyBag.add(eur12);
		assertTrue("addMoneyWithEmptyMoneyBagOrNullShouldReturnMoney",result instanceof Money);
		assertEquals("add2MoneyBagWithDifferentAndSameCurrencyThenCheckSumAndNewCurrency - Check Amount",12,((Money)result).getAmount(),0);			
	}


}
