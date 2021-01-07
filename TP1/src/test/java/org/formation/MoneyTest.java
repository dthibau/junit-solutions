package org.formation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MoneyTest {

    private Money eur12,eur14,yen12;

    @BeforeEach
    public void setUp() throws Exception {
        eur12 = new Money(12,"EUR");
        eur14 = new Money(14,"EUR");
        yen12 = new Money(12,"YEN");
    }

    @Test
    public void addWithSameCurrency() {
        Money result = eur12.add(eur14);
        assertAll("SimpleAdd",
                () -> assertEquals("EUR",result.getCurrency()),
                () -> assertEquals(26.0, result.getAmount())
        );
    }

    @Test
    public void addWithDifferentCurrencyShouldThrowException() {
        Throwable exception =
                assertThrows(IllegalArgumentException.class,
                        () -> { eur12.add(yen12);});
    }

    @Test
    public void testEquals() {
        Money eur12Bis = new Money(12, "EUR");
        assertAll("Equals",
                () -> assertEquals(eur12Bis,eur12),
                () -> assertEquals(eur12,eur12),
                () -> assertNotEquals(eur12,yen12)
        );
    }
}
