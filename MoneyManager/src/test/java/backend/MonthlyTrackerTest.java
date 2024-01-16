package backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MonthlyTrackerTest {

    @Test
    void totalExpenses() {
        double[] expenses = {2000, 300, 75, 100, 300, 50, 200, 100, 50, 25, 50, 50, 25.34};
        double expected = 3325.34;
        double actual = MoneyManagerEngine.totalExpenses(expenses);
        assertEquals(expected, actual);
    }

    @Test
    void totalExpensesNoCosts() {
        double[] expenses = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double expected = 0;
        double actual = MoneyManagerEngine.totalExpenses(expenses);
        assertEquals(expected, actual);
    }

    @Test
    void totalExpensesEmpty() {
        double[] expenses = {};
        double expected = 0;
        double actual = MoneyManagerEngine.totalExpenses(expenses);
        assertEquals(expected, actual);
    }

    @Test
    void excessLessThanIncome() {
        double[] expenses = {2000, 300, 75, 100, 300, 50, 200, 100, 50, 25, 50, 50, 25.34};
        double income = 5650;
        double actual = MoneyManagerEngine.excess(income, expenses);
        double expected = 2324.66;
        assertEquals(expected, actual);
    }

    @Test
    void excessMoreThanIncome() {
        double[] expenses = {2000, 300, 75, 100, 300, 50, 200, 100, 50, 25, 50, 50, 25.34};
        double income = 1000;
        double actual = MoneyManagerEngine.excess(income, expenses);
        double expected = -2325.34;
        assertEquals(expected, actual);
    }
}