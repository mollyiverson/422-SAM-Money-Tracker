package backend;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
public class GoalTest {
    @Test
    void testAttainable(){
        String goal = "TV, 100, 1000, 200";
        double actual = MoneyManagerEngine.attainability(goal);
        double expected = .5;
        assertEquals(actual, expected);
    }
    @Test
    void testNotAttainable(){
        String goal = "TV, 100, 1000, 1";
        double actual = MoneyManagerEngine.attainability(goal);
        double expected = -1;
        assertEquals(actual, expected);

    }

    @Test
    void testHighSavingAdvice(){
        double[] actual = MoneyManagerEngine.savingAdvice(1000, 2000, "High");
        double[] expected = {33.33, 60};
        assertEquals(actual[0], expected[0], .1);
        assertEquals(actual[1], expected[1], .1);
    }

    @Test
    void testMediumSavingAdvice(){
        double[] actual = MoneyManagerEngine.savingAdvice(1000, 2000, "Medium");
        double[] expected = {16.67, 119.98};
        assertEquals(actual[0], expected[0], .1);
        assertEquals(actual[1], expected[1], .1);
    }

    @Test
    void testLowSavingAdvice(){
        double[] actual = MoneyManagerEngine.savingAdvice(1000, 2000, "Low");
        double[] expected = {11.11, 180.02};
        assertEquals(actual[0], expected[0], .1);
        assertEquals(actual[1], expected[1], .1);
    }

    @Test
    void testGetNotAttainable(){
        String actual = MoneyManagerEngine.getAttainable("100", -1);
        String expected = "Savings goal is unattainable";
        assertEquals(actual, expected);
    }

    @Test
    void testGetAttainable(){
        String actual = MoneyManagerEngine.getAttainable("1000", 17.898);
        String expected = "Save $" + InputHandler.formatDoubles(17.90) + " daily to reach goal";;
        assertEquals(actual, expected);
    }

    @Test
    void toZero(){
        JTextField field = new JTextField();
        MoneyManagerEngine.toZero(field);
        assertEquals(field.getText(), "0");
        field.setText("2");
        MoneyManagerEngine.toZero(field);
        assertEquals(field.getText(), "2");

    }


}
