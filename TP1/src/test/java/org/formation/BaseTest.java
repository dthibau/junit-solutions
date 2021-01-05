package org.formation;

import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected Money eur12,eur14,yen12;
    protected MoneyBag mbVide, mb12Eur, mbb14Eur12Yen;

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
}
