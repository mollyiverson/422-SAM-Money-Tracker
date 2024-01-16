package backend;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @Test
    void isValidDouble() {
        String notValid = "test";
        String valid = "11.2";

        assertTrue(InputHandler.isValidDouble(valid));
        assertFalse(InputHandler.isValidDouble(notValid));
    }

    @Test
    void formatDoubles() {
        double value = 11.23432;
        String expected = "11.23";
        String actual = InputHandler.formatDoubles(value);
        assertEquals(expected, actual);
    }

    @Test
    void isValidInt() {
        String notValid = "test";
        String valid = "11";

        assertTrue(InputHandler.isValidInt(valid));
        assertFalse(InputHandler.isValidInt(notValid));
    }

    @Test
    void test_valid_date() {
        int year = 2010;
        Date expected = new Date(year - 1900, Calendar.MARCH, 5);
        Date actual = InputHandler.isValidDate("03/05/2010");

        assertTrue(actual.equals(expected));
    }

    @Test
    void test_invalid_date() {
        Date invalidDate = InputHandler.isValidDate("invalid input");
        assertNull(invalidDate);
    }

    @Test
    void appendToArray() {
        String[] holder = {"10", "2"};
        String[][] ogArray = {{"10", "2"}, {"8", "9"}};
        String[][] actual = InputHandler.appendToArray(ogArray, holder);
        String[][] expected = {{"10", "2"}, {"8", "9"}, holder};

        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void validHouseholdSize() {
        String notValid = "0";
        String valid = "3";
        String notValid2 = "test";

        assertTrue(InputHandler.validHouseholdSize(valid));
        assertFalse(InputHandler.validHouseholdSize(notValid));
        assertFalse(InputHandler.validHouseholdSize(notValid2));
    }

    @Test
    void initializeInputHandler() {
        assertDoesNotThrow(InputHandler::new);
    }
}