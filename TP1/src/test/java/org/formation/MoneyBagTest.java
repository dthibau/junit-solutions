package org.formation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MoneyBagTest extends BaseTest {

    @Mock
    ConvertService convertService;

    @BeforeEach
    public void setup() throws Exception {
        super.setUp();
        mbVide.setConvertService(convertService);
        mb12Eur.setConvertService(convertService);
        mbb14Eur12Yen.setConvertService(convertService);
    }



    @Test
    public void whenVideAmountIs0() {
        Money result = mbVide.convertInto("EUR");

        assertThat("Conversion of an empty MoneyBag",
                result, allOf(hasProperty("currency", equalTo("EUR")),
                        hasProperty("amount",equalTo(0.0))));

        verifyNoInteractions(convertService);
    }

    @Test
    public void convertOnlyOneCurrency() {
        when(convertService.convert(12, "EUR", "YEN")).thenReturn(20.5);

        Money result = mb12Eur.convertInto("YEN");

        assertThat("Conversion of a MoneyBag with only one currency",
                result, allOf(hasProperty("currency", equalTo("YEN")),
                        hasProperty("amount",equalTo(20.5))));
        verify(convertService, times(1)).convert(12,"EUR","YEN");
        verifyNoMoreInteractions(convertService);

    }

    @Test
    public void convertSeveralCurrencies() {
        when(convertService.convert(14, "EUR", "YEN")).thenReturn(20.5);

        Money result = mbb14Eur12Yen.convertInto("YEN");

        assertThat("Conversion of a MoneyBag with only one currency",
                result, allOf(hasProperty("currency", equalTo("YEN")),
                        hasProperty("amount",equalTo(32.5))));

        verify(convertService, times(1)).convert(14,"EUR","YEN");
        verifyNoMoreInteractions(convertService);
    }
}
