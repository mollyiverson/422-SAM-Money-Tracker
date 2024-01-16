package backend;

import GUI.MoneyManagerGUI;

import backend.InputHandler;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class MoneyManagerEngine {

    /**
     * Calculates the total expenses of the user.
     *
     * @param expenses an array of doubles representing all the expenses.
     * @return the sum of the total expenses in the array.
     */
    public static double totalExpenses(double[] expenses) {
        double sum = 0;
        for (double expense : expenses) {
            sum += expense;
        }
        return sum;
    }

    /**
     * Calculates the excess money after expenses are paid.
     *
     * @param income   The user's income.
     * @param expenses An array of the user's expenses.
     * @return The income kept after expenses are taken out.
     */
    public static double excess(double income, double[] expenses) {
        return income - totalExpenses(expenses);
    }

    /**
     * finds if a goal is attainable
     *
     * @param goal string with all goal information
     * @return value to save daily if goal is attainable. -1 if not.
     */
    public static double attainability(String goal) {
        String[] arrOfGoalStr = goal.split(",", 4);
        //0 = name, 1 = goal, 2 = excess, 3 = time
        double goalValue = Double.parseDouble(arrOfGoalStr[1]);
        double excessValue = Double.parseDouble(arrOfGoalStr[2]);
        double timeValue = Double.parseDouble(arrOfGoalStr[3]);

        double dailyCharge = goalValue / timeValue;
        double dailyExtra = excessValue / 30.0;

        if (dailyCharge < dailyExtra) {
            return dailyCharge;
        }

        return -1;
    }

    /**
     * When goal is unattainable, gives advice on how to reach it
     *
     * @param excess excess budget
     * @param goal   amount to save
     * @return double with
     */
    public static double[] savingAdvice(double excess, double goal, String priority) {
        double[] saving = {0.0, 0.0};
        double days = 0.0;
        double highValue = (excess) / 30;
        double mediumValue = highValue / 2;
        double lowValue = highValue / 3;

        if (Objects.equals(priority, "High")) {
            days = goal / highValue;
            saving[0] = highValue;
        } else if (Objects.equals(priority, "Medium")) {
            days = goal / mediumValue;
            saving[0] = mediumValue;
        } else  {
            days = goal / lowValue;
            saving[0] = lowValue;
        }
        saving[1] = days;
        return saving;
    }

    /**
     * Creates a string to print in the attainability field
     *
     * @param goal string with data from UI
     * @return string to print in field
     */
    public static String getAttainable(String goal, double value) {
        if (value == -1) {
            return "Savings goal is unattainable";
        } else {
            return "Save $" + InputHandler.formatDoubles(value) + " daily to reach goal";
        }
    }

    public static void toZero(JTextField field){
        if (Objects.equals(field.getText(), "")){
            field.setText("0");
        }
    }

}
