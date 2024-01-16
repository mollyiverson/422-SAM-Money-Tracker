package backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {
    @Test
    void addTaxRates_valid_filepath() {
        assertDoesNotThrow(() -> new TaxCalculator("salesTax.csv"));
    }

    @Test
    void addTaxRates_valid_filepath2() {
        assertDoesNotThrow(() -> new TaxCalculator());
    }
}