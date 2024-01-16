package backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class handles validating and editing input to be usable by the GUI and backend
 */
public class InputHandler {

    /**
     * Checks if a string value is a double
     *
     * @param str string value to be tested
     * @return true if double, false if not
     */
    public static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Formats a double to have two cent places
     *
     * @param value double
     * @return formatted double string
     */
    public static String formatDoubles(double value) {
        return String.format("%.2f", value);
    }

    /**
     * Checks if a string value is an integer
     *
     * @param str string value to be tested
     * @return true if integer, false if not
     */
    public static boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Determines if a string is in a valid date format (MM/DD/YYYY)
     *
     * @param str string date to be tested
     * @return the Date object if valid, null if not
     */
    public static Date isValidDate(String str) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            return dateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * appends an 1-D array onto a 2-D array
     *
     * @param originalArray 2-D array
     * @param toAppend      1-D array
     * @return newly made 2-D array
     */
    public static String[][] appendToArray(String[][] originalArray, String[] toAppend) {
        String[][] newArray = new String[originalArray.length + 1][];

        for (int i = 0; i < originalArray.length; i++) {
            newArray[i] = originalArray[i].clone();
        }

        newArray[newArray.length - 1] = toAppend;

        return newArray;
    }

    /**
     * Checks that the household size makes sense.
     *
     * @param size the number of people in a household.
     * @return if the household size is a valid number.
     */
    public static Boolean validHouseholdSize(String size) {
        int num = 0;
        try {
            num = Integer.parseInt(size);
        } catch (Exception e) {
            return false;
        }
        return num > 0;
    }

}
