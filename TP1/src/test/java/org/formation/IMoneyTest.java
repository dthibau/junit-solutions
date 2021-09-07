package org.formation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IMoneyTest {

    private Money eur12,eur14,yen12;
    private MoneyBag mbVide, mb12Eur, mbb14Eur12Yen;

    @BeforeEach
    public void setUp() throws Exception {
        eur12 = new Money(12,"EUR");
        eur14 = new Money(14,"EUR");
        yen12 = new Money(12,"YEN");
        mbVide = new MoneyBag();
        mb12Eur = new MoneyBag();
        mb12Eur.put(eur12);

        mbb14Eur12Yen = new MoneyBag();
        mbb14Eur12Yen.put(eur14);
        mbb14Eur12Yen.put(yen12);
    }

    @Test
    public void add2MoneyWithSameCurrency() {
        IMoney result = eur12.add(eur14);
        assertThat(result).as("Check IMoney Type and result").isExactlyInstanceOf(Money.class)
        				.extracting("currency","amount")
        				.containsExactly("EUR",25);
        
        
    }

    @Test
    public void add2MoneyWithDifferentCurrency() {
        IMoney result = eur14.add(yen12);
        assertThat(result).as("Check IMoney Type and result").isExactlyInstanceOf(MoneyBag.class)
        				.extracting(r -> ((MoneyBag) r).getCurrencyAmount("EUR"), r -> ((MoneyBag) r).getCurrencyAmount("YEN"))
        				.containsExactly(14.0, 12.0);        
        assertThat(((MoneyBag)result).getCurrencies()).containsExactly("EUR","YEN");
        			
    }

    @Test
    public void addMoneyWithMoneyBagNotContainingCurrency() {
        IMoney result = mb12Eur.add(yen12);
        
        assertThat(result).as("Check MoneyBag Type and result").isExactlyInstanceOf(MoneyBag.class)
		.extracting(r -> ((MoneyBag) r).getCurrencyAmount("EUR"), r -> ((MoneyBag) r).getCurrencyAmount("YEN"))
		.containsExactly(12.0, 12.0);
    }
    @Test
    public void addMoneyBagWithMoneyNotContainingCurrency() {
        IMoney result = yen12.add(mb12Eur);
        assertAll("addMoneyWithMoneyBagNotContainingCurrency",
                () -> assertTrue( result instanceof MoneyBag),
                () -> assertEquals(12,((MoneyBag)result).getCurrencyAmount("EUR")),
                () -> assertEquals(12, ((MoneyBag)result).getCurrencyAmount("YEN"))
        );
    }
    @Test
    public void addMoneyWithMoneyBagContainingCurrency() {
        IMoney result = mbb14Eur12Yen.add(yen12);
        assertAll("addMoneyWithMoneyBagNotContainingCurrency",
                () -> assertTrue( result instanceof MoneyBag),
                () -> assertEquals(14,((MoneyBag)result).getCurrencyAmount("EUR")),
                () -> assertEquals(24, ((MoneyBag)result).getCurrencyAmount("YEN"))
        );
    }
    @Test
    public void addMoneyBagWithMoneyContainingCurrency() {
        IMoney result = yen12.add(mbb14Eur12Yen);
        assertAll("addMoneyWithMoneyBagNotContainingCurrency",
                () -> assertTrue( result instanceof MoneyBag),
                () -> assertEquals(14,((MoneyBag)result).getCurrencyAmount("EUR")),
                () -> assertEquals(24, ((MoneyBag)result).getCurrencyAmount("YEN"))
        );
    }
    @Test
    public void addMoneyBagWithMoneyBagContainingSameCurrencies() {
        IMoney result = mbb14Eur12Yen.add(mbb14Eur12Yen);
        assertAll("addMoneyBagWithMoneyBagContainingSameCurrencies",
                () -> assertTrue( result instanceof MoneyBag),
                () -> assertEquals(28,((MoneyBag)result).getCurrencyAmount("EUR")),
                () -> assertEquals(24, ((MoneyBag)result).getCurrencyAmount("YEN"))
        );
    }

    @Test
    public void addMoneyWithEmptyMoneyBagShouldReturnMoney() {
        IMoney result = mbVide.add(eur12);
        assertAll("addMoneyWithEmptyMoneyBagShouldReturnMoney",
                () -> assertTrue( result instanceof Money),
                () -> assertEquals("EUR",((Money)result).getCurrency()),
                () -> assertEquals(12, ((Money)result).getAmount())
        );
    }
    @Test
    public void addEmptyMoneyBagWithMoneyShouldReturnMoney() {
        IMoney result = eur12.add(mbVide);
        assertAll("addEmptyMoneyBagWithMoneyShouldReturnMoney",
                () -> assertTrue( result instanceof Money),
                () -> assertEquals("EUR",((Money)result).getCurrency()),
                () -> assertEquals(12, ((Money)result).getAmount())
        );
    }
}
