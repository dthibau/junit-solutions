package org.formation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConvertArgumentsProvider implements ArgumentsProvider {
    protected Money eur12,eur14,yen12;
    protected MoneyBag mbVide, mb12Eur, mbb14Eur12Yen;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

        eur12 = new Money(12,"EUR");
        eur14 = new Money(14,"EUR");
        yen12 = new Money(12,"YEN");
        mbVide = new MoneyBag();
        mb12Eur = new MoneyBag();
        mb12Eur.put(eur12);
        mbb14Eur12Yen = new MoneyBag();
        mbb14Eur12Yen.put(eur14);
        mbb14Eur12Yen.put(yen12);

        return Stream.of(arguments(mbVide, 0.0),
                arguments(mb12Eur, 20.5),
                arguments(mbb14Eur12Yen, 32.5));
    }
}
