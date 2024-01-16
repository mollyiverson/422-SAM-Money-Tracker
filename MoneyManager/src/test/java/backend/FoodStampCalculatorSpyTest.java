package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodStampCalculatorSpyTest {

    FoodStampCalculator foodStampCalculator = new FoodStampCalculator(true);
    FoodStampCalculator foodStampCalculatorSpy = spy(foodStampCalculator);

    @BeforeEach
    void setUp(){
        when(foodStampCalculatorSpy.addFoodStampRates(anyString())).thenAnswer(I -> {
            foodStampCalculatorSpy.addRate("NE", 1.65);
            foodStampCalculatorSpy.addRate("OR", 2);
            foodStampCalculatorSpy.addRate("RI", 1.85);
            foodStampCalculatorSpy.addRate("KS", 1.3);
            foodStampCalculatorSpy.addRate("HI", 2.0);
            foodStampCalculatorSpy.addRate("AK", 1.3);
            return true;
        });
        foodStampCalculatorSpy.addFoodStampRates("path");
    }

    @Test
    void eligibleForFoodStamps130Eligible() {
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("KS", 1000, 2));
    }

    @Test
    void eligibleForFoodStamps130NotEligible() {
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("KS", 2250, 2));
    }

    @Test
    void eligibleForFoodStamps185Eligible() {
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("RI", 1000, 3));
    }

    @Test
    void eligibleForFoodStamps185NotEligible() {
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("RI", 3950.75, 3));
    }

    @Test
    void eligibleForFoodStamps200Eligible() {
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("OR", 3000, 3));
    }

    @Test
    void eligibleForFoodStamps200NotEligible() {
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("OR", 5000, 3));
    }

    @Test
    void eligibleForFoodStamps165Eligible() {
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("NE", 3000, 3));
    }

    @Test
    void eligibleForFoodStamps165NotEligible() {
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("NE", 5000, 3));
    }
    @Test
    void eligibleForFoodStampsHawaiiEligible(){
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("HI", 4000, 3));
    }

    @Test
    void eligibleForFoodStampsHawaiiNotEligible(){
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("HI", 5000, 3));
    }
    @Test
    void eligibleForFoodStampsAlaskaEligible(){
        assertTrue(foodStampCalculatorSpy.eligibleForFoodStamps("AK", 3365, 3));
    }

    @Test
    void eligibleForFoodStampsAlaskaNotEligible(){
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("AK", 3366, 3));
    }

    @Test
    void invalidState(){
        assertFalse(foodStampCalculatorSpy.eligibleForFoodStamps("XY", 3366, 3));
    }
}