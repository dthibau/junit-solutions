package org.formation;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import static org.junit.jupiter.params.provider.Arguments.arguments;


public class MoneyBagProvider implements ArgumentsProvider {

	Money eur12,eur14,yen12,usd12;
	MoneyBag mb12Eur,mb14Eur12Yen,mb12usd12Yen;
	

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		eur12 = new Money(12,"EUR");
        eur14 = new Money(14,"EUR");
        yen12 = new Money(12,"YEN");
        usd12 = new Money(12,"USD");
        
        mb12Eur = new MoneyBag();
        mb12Eur.put(eur12);
        mb14Eur12Yen = new MoneyBag();
        mb14Eur12Yen.put(eur14);
        mb14Eur12Yen.put(yen12);
        mb12usd12Yen = new MoneyBag();
        mb12usd12Yen.put(usd12);
        mb12usd12Yen.put(yen12);

        return Stream.of(arguments(mb12Eur, new Money(10,"USD"), 1),
                arguments(mb14Eur12Yen, new Money(20,"USD"),2),
                arguments(mb12usd12Yen, new Money(22,"USD"), 1),
                arguments(new MoneyBag(), new Money(0,"USD"), 0));
	}

}
