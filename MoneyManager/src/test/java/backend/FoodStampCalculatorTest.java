package backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests some methods that the FoodStampCalculatorSpyTest can't
 */
public class FoodStampCalculatorTest {

    @Test
    void addFoodStampRates_valid_filepath() {
        assertDoesNotThrow(() -> new FoodStampCalculator("FoodStampsRate.csv"));
    }

    @Test
    void addFoodStampRates_valid_filepath2() {
        assertDoesNotThrow(() -> new FoodStampCalculator());
    }

    @Test
    void getRates() {
        FoodStampCalculator foodStampCalculator = new FoodStampCalculator("FoodStampsRate.csv");
        assertNotNull(foodStampCalculator.getFoodStampRates());
    }
}
