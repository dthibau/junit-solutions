package org.formation.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("Testing Currency Validator When using Invalid data")
@ExtendWith(CurrencyParameterResolver.class)
public class CurrencyValidatorTest {


    @RepeatedTest(value=3)
    @DisplayName("All currencies are invalid")
    public void validateCurrency(String currency, TestReporter reporter) {
        reporter.publishEntry("Validating "+currency);
        assertThrows(
                CurrencyValidator.ValidationException.class,
                () -> CurrencyValidator.validate(currency));
    }

}
