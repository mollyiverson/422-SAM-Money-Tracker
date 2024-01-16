package GUI;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

import backend.*;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.apache.groovy.parser.antlr4.util.StringUtils.isEmpty;

public class MoneyManagerGUI extends JFrame {
    private final ArrayList<String> goals = new ArrayList<>();
    private final ArrayList<String> transactions = new ArrayList<>();
    public double costValue = 0.0;
    public String itemTransaction = "";
    public String categoryTransaction = "";
    public String dateTransaction = "";
    private JPanel panel1;
    private JTabbedPane tabbedPane;
    private JPanel monthlyExpensesPanel;
    private JPanel savingGoalPanel;
    private JPanel monthlySummaryPanel;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem undo;
    private JMenuItem redo;
    private JLabel totalIncome;
    private JLabel rentMortgage;
    private JLabel autoExpenses;
    private JLabel utilities;
    private JLabel transportation;
    private JLabel groceries;
    private JLabel entertainment;
    private JLabel debt;
    private JLabel savings;
    private JLabel investments;
    private JLabel medical;
    private JLabel other;
    private JLabel diningOut;
    private JLabel personalCare;
    private JLabel totalExpenses;
    private JLabel excess;
    private JLabel excessAnswer;
    private JLabel expensesAnswer;
    private JTextField rentField;
    private JTextField autoField;
    private JTextField utilitiesField;
    private JTextField transportationField;
    private JTextField groceriesField;
    private JTextField entertainmentField;
    private JTextField debtField;
    private JTextField savingsField;
    private JTextField investmentsField;
    private JTextField medicalField;
    private JTextField otherField;
    private JTextField diningField;
    private JTextField personalField;
    private JTextField incomeField;
    private JButton calculateBut;
    private JPanel savingsGoalPanel;
    private JTextField goalField;
    private JLabel savingsGoal;
    private JTextField itemField;
    private JLabel itemSaving;
    private JLabel timeFrame;
    private JTextField timeField;
    private JLabel attainable;
    private JLabel attainableField;
    private JLabel excessField;
    private JButton calculateGoal;
    private JLabel attainableLabel;
    private JButton goalClear;
    private JButton undoButton;
    private JButton redoButton;
    private JTable goalTable;
    private JPanel spendingTrackerPanel;
    private JButton addPurchaseButton;
    private JTextField itemNameField;
    private JTextField costField;
    private JTextField dateField;
    private JComboBox categoryOptions;
    private JLabel itemName;
    private JLabel cost;
    private JLabel date;
    private JLabel category;
    private JComboBox prioritySelect;
    private JTable transactionTable;
    private JComboBox stateField;
    private JLabel stateLabel;
    private JTextField householdSizeValue;
    private JLabel householdSizeLabel;
    private JLabel fSEligibleLabel;
    private JLabel fSEligibleValue;
    private JCheckBox taxCheckBox;
    private JLabel taxLabel;
    private JLabel taxDetails;
    private JButton clearTracker;
    private String goalPrevValueHolder;
    private String goalPriority = "";   //gets set to the priority when goal calculated
    private double excessValue = 0.0;   //gets set to the excess when goal calculated

    private double goalValue = 0.0;     //gets set to the goal when goal calculated


    private double timeValue = 0.0;     //gets set to the time (days) when goal calculated

    public MoneyManagerGUI() {
            setScreen();
            setNames();
        //Action performed for when the monthly expenses button is pressed
        calculateBut.addActionListener(e -> {
            // if input is valid, converts to doubles and calculates the total expenses/month and the excess money after expenses are paid.
            if (!InputHandler.isValidDouble(rentField.getText()) || !InputHandler.isValidDouble(autoField.getText()) || !InputHandler.isValidDouble(utilitiesField.getText()) || !InputHandler.isValidDouble(transportationField.getText()) || !InputHandler.isValidDouble(groceriesField.getText()) || !InputHandler.isValidDouble(entertainmentField.getText()) || InputHandler.isValidDouble(debtField.getText()) || !InputHandler.isValidDouble(savingsField.getText()) || !InputHandler.isValidDouble(investmentsField.getText()) || !InputHandler.isValidDouble(medicalField.getText()) || !InputHandler.isValidDouble(diningField.getText()) || !InputHandler.isValidDouble(personalField.getText()) || !InputHandler.isValidDouble(otherField.getText())) {
                setMonthlyValuesZero();
            }
            setIncomeAndExcessInfo();
        });

        //actions performed when calculate in savings goal is pressed
        calculateGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (setGoalValues()) {
                    calculateGoalButton();
                    loadFromGoalArray();
                }

            }
        });
        //actions performed when clear button in goals in pressed
        goalClear.addActionListener(e -> clearGoalFields());



        dateField.addFocusListener(new FocusAdapter() {
            // Makes default date format "MM/DD/YYYY" disappear when user starts to type
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (dateField.getText().equals("MM/DD/YYYY")) {
                    dateField.setText("");
                }
            }

            // Shows default date format "MM/DD/YYYY" in date field when user hasn't inputted anything
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (dateField.getText().isEmpty()) {
                    dateField.setText("MM/DD/YYYY");
                }
            }
        });
        addPurchaseButton.addActionListener(new ActionListener() {
            // Adds a new transaction to the transaction table
            public void actionPerformed(ActionEvent e) {
                // Validate fields
                if (setTransactionValues()) {
                    addTransaction();
                    loadFromTransactionArray();
                    clearTransactionFields();
                }
            }
        });

        // Clears spending tracker when clear button is pressed by user
        clearTracker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTransactionFields();
                transactions.clear();
                String[] columns = {"1", "2", "3", "4"};
                String[][] data = {{"Name", "Cost", "Date", "Category"}};
                DefaultTableModel model = new DefaultTableModel(data, columns);
                transactionTable.setModel(model);
            }
        });
    }


    /**
     * Makes a string with all the information from the goal page.
     * Checks if inputs are valid
     *
     * @return string with information. empty string if a field is empty.
     */
    public String makeGoalString() {
        String goal = goalField.getText();
        String excess = excessField.getText();
        String time = timeField.getText();
        String name = itemField.getText();
        if (Objects.equals(goal, "") || Objects.equals(excess, "") || Objects.equals(time, "") || Objects.equals(name, "")) {
            return "";
        }
        return name + "," + goal + "," + excess + "," + time;
    }

    /**
     * Makes a string with all the information from transaction
     * Checks if inputs are valid
     *
     * @return string with information. empty string if a field is empty.
     */
    public String makeTransactionString() {
        String name = itemNameField.getText();
        String category = Objects.requireNonNull(categoryOptions.getSelectedItem()).toString();
        String cost;

        if (taxCheckBox.isSelected()) {
            TaxCalculator taxCalculator = new TaxCalculator();
            // Calculates the tax if selected by the user
            double initialCost = Double.parseDouble(costField.getText());
            costValue = taxCalculator.calculateTotalCost(initialCost, Objects.requireNonNull(stateField.getSelectedItem()).toString(), Objects.requireNonNull(categoryOptions.getSelectedItem()).toString());
            cost = InputHandler.formatDoubles(costValue);
        } else {
            cost = costField.getText();
        }
        String date = dateField.getText();
        if (Objects.equals(name, "") || Objects.equals(category, "") || Objects.equals(cost, "") || Objects.equals(date, "")) {
            return "";
        }
        return name + "," + cost + "," + date + "," + category;
    }

    /**
     * clears all fields when the clear button is pressed
     */
    private void clearGoalFields() {
        goalField.setText("");
        timeField.setText("");
        itemField.setText("");
        attainableLabel.setText("");
        goalValue = 0.0;
        goalPriority = "";
        timeValue = 0.0;
        goals.clear();

        String[] columns = {"1", "2", "3", "4", "5"};
        String[][] data = {{"Name", "Goal", "Excess", "Time", "Attainable?"}};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        goalTable.setModel(model);
    }

    /**
     * clears all transaction fields when the clear button is pressed
     */
    private void clearTransactionFields() {
        costField.setText("");
        dateField.setText("MM/DD/YYYY");
        itemNameField.setText("");
        attainableLabel.setText("");
        costValue = 0.0;
        itemTransaction = "";
        categoryTransaction = "";
        String dateTransaction = "";
        this.taxCheckBox.setSelected(false);
    }

    /**
     * Runs the calculation methods when the calculate button in savings goal is pressed.
     * If the goal is unattainable, gives advice on how to save based on goal priority
     */
    private void calculateGoalButton() {
        String goalString = makeGoalString();
        if (!Objects.equals(goalString, "")) {
            double value = MoneyManagerEngine.attainability(goalString);
            String attainableString = MoneyManagerEngine.getAttainable(goalString, value);
            if (value == -1) {
                double[] calcValues = MoneyManagerEngine.savingAdvice(excessValue, goalValue, goalPriority);
                String advice = ". Try saving $" + InputHandler.formatDoubles(calcValues[0]) + " per day for " + InputHandler.formatDoubles(calcValues[1]) + " days";
                attainableString += advice;
            }
            attainableLabel.setText(attainableString);
            goalString += "," + attainableString;
            goals.add(goalString);
        }
    }

    /**
     * Runs the calculation methods when the calculate button in savings goal is pressed.
     * If the goal is unattainable, gives advice on how to save based on goal priority
     */
    public void addTransaction() {
        String transactionString = makeTransactionString();
        if (!Objects.equals(transactionString, "")) {
            transactions.add(transactionString);
        }
    }



    /**
     * displays all the goals from the array
     */
    private void loadFromGoalArray() {
        String[] columns = {"1", "2", "3", "4", "5"};
        String[][] data = {{"Name", "Goal", "Excess", "Time", "Attainable?"}};
        for (String goal : goals) {
            String[] arrOfGoalStr = goal.split(",", 5);
            data = InputHandler.appendToArray(data, arrOfGoalStr);
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        goalTable.setModel(model);

    }

    /**
     * displays all the transactions from the array
     */
    private void loadFromTransactionArray() {
        String[] columns = {"1", "2", "3", "4"};
        String[][] data = {{"Name", "Cost", "Date", "Category"}};
        for (String purchase : transactions) {
            String[] arrOfTransactionStr = purchase.split(",", 4);
            data = InputHandler.appendToArray(data, arrOfTransactionStr);
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        transactionTable.setModel(model);
    }

    /**
     * sets the variables for the goal.
     *
     * @return false if any of the values aren't valid, true if they all are.
     */
    private boolean setGoalValues() {
        boolean returnValue = false;
        goalPriority = (String) prioritySelect.getSelectedItem();
        if (InputHandler.isValidDouble(goalField.getText())  &&
                InputHandler.isValidDouble(timeField.getText())) {
            goalValue = Double.parseDouble(goalField.getText());
            excessValue = Double.parseDouble(excessField.getText());
            timeValue = Double.parseDouble(timeField.getText());
            return true;
        }
        else {
            clearGoalFields();
            return false;
        }
    }

    /**
     * sets the variables for the transaction.
     *
     * @return false if any of the values aren't valid, true if they all are.
     */
    private boolean setTransactionValues() {
        if (InputHandler.isValidDouble(costField.getText())) {
            costValue = Double.parseDouble(costField.getText());
        } else {
            clearTransactionFields();
            showMessageDialog(null, "Please enter an integer or double for cost");
            return false;
        }
        String item = itemNameField.getText();
        if (!Objects.equals(item, "")) {
            itemTransaction = itemNameField.getText();
        } else {
            clearTransactionFields();
            showMessageDialog(null, "Please enter item name");
            return false;
        }
        Date date = InputHandler.isValidDate(dateField.getText());
        if (date != null) {
            dateTransaction = dateField.getText();
        } else {
            clearTransactionFields();
            showMessageDialog(null, "Date is not in correct format");
            return false;
        }
        return true;
    }

    /**
     * sets all monthly value boxes to zero of empty
     *
     */
    private void setMonthlyValuesZero() {
        MoneyManagerEngine.toZero(rentField);
        MoneyManagerEngine.toZero(autoField);
        MoneyManagerEngine.toZero(personalField);
        MoneyManagerEngine.toZero(otherField);
        MoneyManagerEngine.toZero(medicalField);
        MoneyManagerEngine.toZero(diningField);
        MoneyManagerEngine.toZero(investmentsField);
        MoneyManagerEngine.toZero(savingsField);
        MoneyManagerEngine.toZero(debtField);
        MoneyManagerEngine.toZero(entertainmentField);
        MoneyManagerEngine.toZero(groceriesField);
        MoneyManagerEngine.toZero(transportationField);
        MoneyManagerEngine.toZero(utilitiesField);
    }

    private void setScreen(){
        this.setContentPane(this.panel1);
        this.setTitle("Money Manager GUI");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.stateField.setSelectedItem("WA");
    }


    private void setNames()
    {
        //InputHandler.isValidDouble(otherField.getText()))
        incomeField.setName("incomeField");
        rentField.setName("rentField");
        diningField.setName("diningField");
        autoField.setName("autoField");
        debtField.setName("debtField");
        otherField.setName("otherField");
        personalField.setName("personalField");
        medicalField.setName("medicalField");
        diningField.setName("diningField");
        savingsField.setName("savingsField");
        investmentsField.setName("investmentsField");
        transportationField.setName("transportationField");
        groceriesField.setName("groceriesField");
        entertainmentField.setName("entertainmentField");
        utilitiesField.setName("utilitiesField");
        calculateBut.setName("calculateBut");
        excessField.setName("excessAnswer");
        expensesAnswer.setName("expensesAnswer");
        timeField.setName("timeField");
        goalField.setName("goalField");
        itemField.setName("itemField");
        calculateGoal.setName("calculateGoal");
        tabbedPane.setName("tabbedPane");
        goalTable.setName("goalTable");
        goalClear.setName("goalClear");
        costField.setName("costField");
        itemNameField.setName("itemNameField");
        dateField.setName("dateField");
        addPurchaseButton.setName("addPurchaseButton");
        transactionTable.setName("transactionTable");
        categoryOptions.setName("categoryOptions");
        clearTracker.setName("clearTracker");
        attainableLabel.setName("attainableLabel");
        householdSizeValue.setName("householdSizeValue");
        fSEligibleValue.setName("fSEligibleValue");
        stateField.setName("stateField");
        taxCheckBox.setName("taxCheckBox");
    }

    /**
     * Checks input is valid and updates the users on food stamp eligibility.
     */
    private void setFoodStampInfo(){
        if (InputHandler.validHouseholdSize(householdSizeValue.getText())) {
            FoodStampCalculator foodStampCalculator = new FoodStampCalculator();
            if (foodStampCalculator.eligibleForFoodStamps(Objects.requireNonNull(stateField.getSelectedItem()).toString(), Double.parseDouble(incomeField.getText()), Integer.parseInt(householdSizeValue.getText()))) {
                fSEligibleValue.setText("yes");
            } else {
                fSEligibleValue.setText("no");
            }

        } else {
            fSEligibleValue.setText("Please ensure the household size is larger than zero");
        }
    }

    /**
     * Checks input is valid and updates the user on the income and excess money.
     */
    private void setIncomeAndExcessInfo(){
        if (InputHandler.isValidDouble(rentField.getText()) && InputHandler.isValidDouble(autoField.getText()) && InputHandler.isValidDouble(utilitiesField.getText()) && InputHandler.isValidDouble(transportationField.getText()) && InputHandler.isValidDouble(groceriesField.getText()) && InputHandler.isValidDouble(entertainmentField.getText()) && InputHandler.isValidDouble(debtField.getText()) && InputHandler.isValidDouble(savingsField.getText()) && InputHandler.isValidDouble(investmentsField.getText()) && InputHandler.isValidDouble(medicalField.getText()) && InputHandler.isValidDouble(diningField.getText()) && InputHandler.isValidDouble(personalField.getText()) && InputHandler.isValidDouble(otherField.getText())) {
            double[] expenses = {Double.parseDouble(rentField.getText()), Double.parseDouble(autoField.getText()), Double.parseDouble(utilitiesField.getText()), Double.parseDouble(transportationField.getText()), Double.parseDouble(groceriesField.getText()), Double.parseDouble(entertainmentField.getText()), Double.parseDouble(debtField.getText()), Double.parseDouble(savingsField.getText()), Double.parseDouble(investmentsField.getText()), Double.parseDouble(medicalField.getText()), Double.parseDouble(diningField.getText()), Double.parseDouble(personalField.getText()), Double.parseDouble(otherField.getText())};
            expensesAnswer.setText(InputHandler.formatDoubles(MoneyManagerEngine.totalExpenses(expenses)));
            // check for valid income value
            if (InputHandler.isValidDouble(incomeField.getText())) {
                excessField.setText(InputHandler.formatDoubles(MoneyManagerEngine.excess(Double.parseDouble(incomeField.getText()), expenses)));
                // Check food stamp eligibility
                setFoodStampInfo();
            } else {
                expensesAnswer.setText("Please ensure all values are numbers");
                excessField.setText("");
            }
        } else {
            expensesAnswer.setText("Please ensure all values are numbers");
            excessField.setText("");
        }
    }
}
