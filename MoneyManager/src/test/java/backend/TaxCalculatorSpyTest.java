package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TaxCalculatorSpyTest {
    TaxCalculator taxCalculator = new TaxCalculator(true);
    TaxCalculator taxCalculatorSpy = spy(taxCalculator);

    @BeforeEach
    void setUp(){
        when(taxCalculatorSpy.addTaxValues(anyString())).thenAnswer(I -> {
            taxCalculatorSpy.addSalesRate("WA", 6.5);
            taxCalculatorSpy.addGroceryRate("WA", 0);
            taxCalculatorSpy.addSalesRate("AR", 6.5);
            taxCalculatorSpy.addGroceryRate("AR", 1.5);
            return true;
        });
        taxCalculatorSpy.addTaxValues("path");
    }

    @Test
    void calculateTotalCost_invalid_category() {
        String state = "WA";
        double initialCost = 10;
        String category = "Fake Category";
        double actualCost = taxCalculatorSpy.calculateTotalCost(initialCost, state, category);
        assertEquals(initialCost, actualCost);
    }


    @Test
    void calculateTotalCost_valid_category_groceries() {
        String state = "AR";
        double initialCost = 10;
        String category = "Groceries";
        double expectedCost = 10.15;
        double actualCost = taxCalculatorSpy.calculateTotalCost(initialCost, state, category);
        assertEquals(expectedCost, actualCost, "Actual cost = " + actualCost + " vs. Expected cost = " + expectedCost);
    }

    @Test
    void calculateTotalCost_valid_category_sales() {
        String state = "AR";
        double initialCost = 10;
        String category = "Entertainment";
        double expectedCost = 10.65;
        double actualCost = taxCalculatorSpy.calculateTotalCost(initialCost, state, category);
        assertEquals(expectedCost, actualCost, "Actual cost = " + actualCost + " vs. Expected cost = " + expectedCost);
    }

    @Test
    void calculateTotalCost_other_category() {
        String state = "WA";
        double initialCost = 500;
        String category = "Auto";
        double actualCost = taxCalculatorSpy.calculateTotalCost(initialCost, state, category);
        assertEquals(initialCost, actualCost);
    }

    @Test
    void invalid_state() {
        String state = "XX";
        double initialCost = 500;
        String category = "Groceries";
        double actualCost = taxCalculatorSpy.calculateTotalCost(initialCost, state, category);
        assertEquals(initialCost, actualCost);
    }
}
