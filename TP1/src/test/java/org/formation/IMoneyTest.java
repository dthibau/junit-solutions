package org.formation;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
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

        assertThat("Add 2 Money of same Currency is just a Money",result, instanceOf(Money.class));
        assertThat("Add 2 Money of same Currency, currency is unchanged and the amount is the sum of the 2 amounts",
                ((Money)result), allOf(hasProperty("currency", equalTo("EUR")),
                        hasProperty("amount",equalTo(26.0))));

    }

    @Test
    public void add2MoneyWithDifferentCurrency() {
        IMoney result = eur14.add(yen12);

        assertThat("Add 2 Money of different Currencies lead to a MoneyBag",result, instanceOf(MoneyBag.class));
        assertThat("Add 2 Money of different Currencies lead to a MoneyBag with only the 2 currencies",
                ((MoneyBag)result).getCurrencies(), containsInAnyOrder("EUR","YEN"));
        assertThat(((MoneyBag)result), allOf(EUR(is(14.0)),YEN(is(12.0))));


    }

    @Test
    public void addMoneyWithMoneyBagNotContainingCurrency() {
        IMoney result = mb12Eur.add(yen12);
        assertAll("addMoneyWithMoneyBagNotContainingCurrency",
                () -> assertTrue( result instanceof MoneyBag),
                () -> assertEquals(12,((MoneyBag)result).getCurrencyAmount("EUR")),
                () -> assertEquals(12, ((MoneyBag)result).getCurrencyAmount("YEN"))
        );
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
    public void add2MoneyBag() {
        IMoney result = mbb14Eur12Yen.add(mbb14Eur12Yen);
        assertAll("addMoneyWithMoneyBagNotContainingCurrency",
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
        assertThat("Add a Money with en empty Money Bag is just a Money",result, instanceOf(Money.class));

    }
    @Test
    public void addEmptyMoneyBagWithMoneyShouldReturnMoney() {
        IMoney result = eur12.add(mbVide);
        assertAll("addEmptyMoneyBagWithMoneyShouldReturnMoney",
                () -> assertTrue( result instanceof Money),
                () -> assertEquals("EUR",((Money)result).getCurrency()),
                () -> assertEquals(12, ((Money)result).getAmount())
        );

        assertThat("Add an empty Money Bag with a Money  is just a Money",result, instanceOf(Money.class));

    }

    public static Matcher<MoneyBag> EUR(Matcher<? super
            Double> matcher) {
        return new FeatureMatcher<MoneyBag, Double>(matcher,
                "a MoneyBag with x Euros", "EUR") {
            @Override
            protected Double featureValueOf(MoneyBag actual) {
                return actual.getCurrencyAmount("EUR");
            }
        };
    }

    public static Matcher<MoneyBag> YEN(Matcher<? super
            Double> matcher) {
        return new FeatureMatcher<MoneyBag, Double>(matcher,
                "a MoneyBag with x Yens", "YEN") {
            @Override
            protected Double featureValueOf(MoneyBag actual) {
                return actual.getCurrencyAmount("YEN");
            }
        };
    }
}
