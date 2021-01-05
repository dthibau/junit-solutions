package org.formation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoneyBagParameterizedTest  {

    @Mock
    ConvertService convertService;


    @ParameterizedTest
    @ArgumentsSource(ConvertArgumentsProvider.class)
    public void convertCase(MoneyBag moneyBag, double amountExpected) {
        moneyBag.setConvertService(convertService);
        lenient().when(convertService.convert( anyDouble(),  anyString(), anyString())).thenReturn(20.5);

        Money result = moneyBag.convertInto("YEN");

        assertThat("Conversion of " + moneyBag + " expect "+amountExpected,
                result, allOf(hasProperty("currency", equalTo("YEN")),
                        hasProperty("amount",equalTo(amountExpected))));
    }

}
