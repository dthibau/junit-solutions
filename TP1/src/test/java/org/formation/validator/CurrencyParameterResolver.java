package org.formation.validator;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

public class CurrencyParameterResolver implements ParameterResolver {
    public static String[] INVALID_CURRENCIES = {
            "USD",
            "CFA",
            "PESOS"
    };

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == String.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return INVALID_CURRENCIES[new Random().nextInt(INVALID_CURRENCIES.length)];
    }
}
