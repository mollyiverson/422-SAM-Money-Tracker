package backend;

import GUI.MoneyManagerGUI;
import org.junit.jupiter.api.*;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.*;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    private FrameFixture window;

    @BeforeEach
    public void setUp() {
        MoneyManagerGUI frame = GuiActionRunner.execute(MoneyManagerGUI::new);
        window = new FrameFixture(frame);
        window.show();
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    void set_budget() {
        JTextComponentFixture incomeText = window.textBox("incomeField");
        JTextComponentFixture rentText = window.textBox("rentField");

        int income = 2000, rent = 1000;

        incomeText.setText(Integer.toString(income));
        rentText.setText(Integer.toString(rent));

        JButtonFixture calculateExcessButton = window.button("calculateBut");
        calculateExcessButton.click();

        JLabelFixture expenses = window.label("expensesAnswer");
        JLabelFixture excess = window.label("excessAnswer");

        expenses.requireText(Integer.toString(rent) + ".00");
        excess.requireText(Integer.toString(income - rent) + ".00");
    }

    void set_goal(String item, String goal, String time)
    {
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Savings Goal");

        JTextComponentFixture goalText = window.textBox("goalField");
        JTextComponentFixture timeText = window.textBox("timeField");
        JTextComponentFixture itemText = window.textBox("itemField");

        goalText.setText(goal);
        timeText.setText(time);
        itemText.setText(item);

        JButtonFixture calculateGoalButton = window.button("calculateGoal");
        calculateGoalButton.click();
    }

    void set_transaction(String cost, String item, String date){
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");

        JTextComponentFixture costField = window.textBox("costField");
        JTextComponentFixture itemNameField = window.textBox("itemNameField");
        JTextComponentFixture dateField = window.textBox("dateField");

        costField.setText(cost);
        itemNameField.setText(item);
        dateField.setText(date);

        JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
        addPurchaseButton.click();
    }


    @Nested
    class Pairwise_Integration_Service_Dao {


        /*************************** setTransactionValues and clearTransactionFields **************/
        @Test
        void setTransaction_clearTransaction_invalid_date(){
            set_transaction("7.25", "hairbrush", "feb 6th");

            JTextComponentFixture costField = window.textBox("costField");
            JTextComponentFixture itemNameField = window.textBox("itemNameField");
            JTextComponentFixture dateField = window.textBox("dateField");
            JCheckBoxFixture taxCheckBox = window.checkBox("taxCheckBox");

            costField.requireText("");
            dateField.requireText("MM/DD/YYYY");
            itemNameField.requireText("");
            taxCheckBox.requireSelected(false);
        }

        @Test
        void setTransaction_clearTransaction_invalid_name(){
            set_transaction("7.25", "", "12/22/2021");

            JTextComponentFixture costField = window.textBox("costField");
            JTextComponentFixture itemNameField = window.textBox("itemNameField");
            JTextComponentFixture dateField = window.textBox("dateField");
            JCheckBoxFixture taxCheckBox = window.checkBox("taxCheckBox");

            costField.requireText("");
            dateField.requireText("MM/DD/YYYY");
            itemNameField.requireText("");
            taxCheckBox.requireSelected(false);
        }

        @Test
        void setTransaction_clearTransaction_invalid_cost(){
            set_transaction("seven", "hairbrush", "12/22/2021");

            JTextComponentFixture costField = window.textBox("costField");
            JTextComponentFixture itemNameField = window.textBox("itemNameField");
            JTextComponentFixture dateField = window.textBox("dateField");
            JCheckBoxFixture taxCheckBox = window.checkBox("taxCheckBox");

            costField.requireText("");
            dateField.requireText("MM/DD/YYYY");
            itemNameField.requireText("");
            taxCheckBox.requireSelected(false);
        }

        /************************* setMonthlyValuesZero and toZero ****************************************/
        // Happy path: all fields not filled out are set to 0
        @Test
        void setMonthly_toZero_happy_path() {
            set_budget();

            JTextComponentFixture incomeText = window.textBox("incomeField");
            JTextComponentFixture rentText = window.textBox("rentField");
            JTextComponentFixture diningText = window.textBox("diningField");
            JTextComponentFixture autoText = window.textBox("autoField");
            JTextComponentFixture utilitiesText = window.textBox("utilitiesField");
            JTextComponentFixture transportationText = window.textBox("transportationField");
            JTextComponentFixture entertainmentText = window.textBox("entertainmentField");
            JTextComponentFixture groceriesText = window.textBox("groceriesField");
            JTextComponentFixture debtText = window.textBox("debtField");
            JTextComponentFixture savingsText = window.textBox("savingsField");
            JTextComponentFixture investmentsText = window.textBox("investmentsField");
            JTextComponentFixture medicalText = window.textBox("medicalField");
            JTextComponentFixture personalText = window.textBox("personalField");
            JTextComponentFixture otherText = window.textBox("otherField");

            incomeText.requireText("2000");
            rentText.requireText("1000");
            diningText.requireText("0");
            autoText.requireText("0");
            utilitiesText.requireText("0");
            transportationText.requireText("0");
            entertainmentText.requireText("0");
            groceriesText.requireText("0");
            debtText.requireText("0");
            savingsText.requireText("0");
            investmentsText.requireText("0");
            medicalText.requireText("0");
            personalText.requireText("0");
            otherText.requireText("0");
        }

        // Other path: if there is invalid input, it is not changed when toZero is called
        @Test
        void setMonthly_toZero_invalid_input() {
            JTextComponentFixture incomeText = window.textBox("incomeField");
            JTextComponentFixture rentText = window.textBox("rentField");
            incomeText.setText("2000");
            rentText.setText("1000");


            JTextComponentFixture diningText = window.textBox("diningField");
            JTextComponentFixture autoText = window.textBox("autoField");
            JTextComponentFixture utilitiesText = window.textBox("utilitiesField");
            JTextComponentFixture transportationText = window.textBox("transportationField");
            JTextComponentFixture entertainmentText = window.textBox("entertainmentField");
            JTextComponentFixture groceriesText = window.textBox("groceriesField");
            JTextComponentFixture debtText = window.textBox("debtField");
            JTextComponentFixture savingsText = window.textBox("savingsField");
            JTextComponentFixture investmentsText = window.textBox("investmentsField");
            JTextComponentFixture medicalText = window.textBox("medicalField");
            JTextComponentFixture personalText = window.textBox("personalField");
            JTextComponentFixture otherText = window.textBox("otherField");

            String invalidAuto = "invalid";
            autoText.setText(invalidAuto);

            JButtonFixture calculateExcessButton = window.button("calculateBut");
            calculateExcessButton.click();

            incomeText.requireText("2000");
            rentText.requireText("1000");
            diningText.requireText("0");
            autoText.requireText(invalidAuto);
            utilitiesText.requireText("0");
            transportationText.requireText("0");
            entertainmentText.requireText("0");
            groceriesText.requireText("0");
            debtText.requireText("0");
            savingsText.requireText("0");
            investmentsText.requireText("0");
            medicalText.requireText("0");
            personalText.requireText("0");
            otherText.requireText("0");
        }


        /************************* setGoalValues and clearGoalFields ****************************************/
        // Happy path: goal input is invalid, so goal fields are cleared
        @Test
        void setGoal_clearGoal_invalid_goal() {
            set_budget();

            String item = "TV";
            int goal = 100;
            String invalidTime = "invalid";

            set_goal(item, invalidTime, Integer.toString(goal));

            JTextComponentFixture goalText = window.textBox("goalField");
            JTextComponentFixture timeText = window.textBox("timeField");
            JTextComponentFixture itemText = window.textBox("itemField");

            goalText.requireText("");
            timeText.requireText("");
            itemText.requireText("");
        }

        // Other path: goal input is valid, so goal fields are not cleared
        @Test
        void setGoal_clearGoal_valid_goal() {
            set_budget();

            String item = "TV";
            int goal = 100;
            int time = 300;

            set_goal(item, Integer.toString(goal), Integer.toString(time));

            JTableFixture goalTable = window.table("goalTable");

            String actualItemName = goalTable.valueAt(TableCell.row(1).column(0));
            assertEquals(actualItemName, item);

            String actualGoal = goalTable.valueAt(TableCell.row(1).column(1));
            assertEquals(actualGoal, Integer.toString(goal));

            String actualTime = goalTable.valueAt(TableCell.row(1).column(3));
            assertEquals(actualTime, Integer.toString(time));

            String actualAdvice = goalTable.valueAt(TableCell.row(1).column(4));
            assertEquals("Save $0.33 daily to reach goal", actualAdvice);
        }

        /************************ AddTransaction and IsValidDate ************************/

        //Path 1: date is in valid form, so transaction is added to table.
        @Test
        void add_transaction_valid_date(){
            String item = "TV";
            String value = "300";
            String date = "12/01/2023";
            JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
            tabbedPane.selectTab("Spending Tracker");
            JTextComponentFixture dateText = window.textBox("dateField");
            JTextComponentFixture costText = window.textBox("costField");
            JTextComponentFixture itemText = window.textBox("itemNameField");
            dateText.setText(date);
            costText.setText(value);
            itemText.setText(item);
            JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
            addPurchaseButton.click();
            JTableFixture transactionTable = window.table("transactionTable");

            String actualItemName = transactionTable.valueAt(TableCell.row(1).column(0));
            assertEquals(actualItemName, item);

            String actualCost = transactionTable.valueAt(TableCell.row(1).column(1));
            assertEquals(actualCost, value);

            String actualDate = transactionTable.valueAt(TableCell.row(1).column(2));
            assertEquals(actualDate, date);
        }

        @Test
        void add_transaction_invalid_date(){
            String item = "TV";
            String value = "300";
            String date = "112/01/20231";
            JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
            tabbedPane.selectTab("Spending Tracker");
            JTextComponentFixture dateText = window.textBox("dateField");
            JTextComponentFixture costText = window.textBox("costField");
            JTextComponentFixture itemText = window.textBox("itemNameField");
            dateText.setText(date);
            costText.setText(value);
            itemText.setText(item);
            JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
            addPurchaseButton.click();

            assertEquals(dateText.text(), "MM/DD/YYYY");
            assertEquals(costText.text(), "");
            assertEquals(itemText.text(), "");

            JTableFixture transactionTable = window.table("transactionTable");
            transactionTable.requireRowCount(0);
        }
    }

    @Nested
    class Neighborhood_Integration_Service_Dao {

        /********************* totalExpenses with nodes excess and setIncomeAndExcessInfo ****************************/

        // excess function calls totalExpenses to get the sum of expenses. There is only one path
        @Test
        void totalExpenses_excess_happy_path() {
            int income = 1000, expense1 = 50, expense2 = 40, expense3 = 20;
            double[] expenses = {expense1, expense2, expense3};
            double actualExcess = MoneyManagerEngine.excess(income, expenses); // calls totalExpenses to get the total
            double expectedExcess = income - (expense1 + expense2 + expense3);
            assertEquals(expectedExcess, actualExcess);
        }

        // The excess is calculated from the budget items and all the inputs are valid
        @Test
        void totalExpenses_setIncomeAnExcessInfo_happy_path()
        {
            int income = 2000, rent = 1000, auto = 40, groceries = 100, personal = 30;
            int totalExpenses = rent + groceries + auto + personal;

            JTextComponentFixture incomeText = window.textBox("incomeField");
            JTextComponentFixture rentText = window.textBox("rentField");
            JTextComponentFixture autoText = window.textBox("autoField");
            JTextComponentFixture groceriesText = window.textBox("groceriesField");
            JTextComponentFixture personalText = window.textBox("personalField");

            incomeText.setText(Integer.toString(income));
            rentText.setText(Integer.toString(rent));
            groceriesText.setText(Integer.toString(groceries));
            personalText.setText(Integer.toString(personal));
            autoText.setText(Integer.toString(auto));

            JButtonFixture calculateExcessButton = window.button("calculateBut");
            calculateExcessButton.click();

            JLabelFixture expenses = window.label("expensesAnswer");
            JLabelFixture excess = window.label("excessAnswer");

            expenses.requireText(Integer.toString(totalExpenses) + ".00");
            excess.requireText(Integer.toString(income - totalExpenses) + ".00");
        }

        // The excess is calculated from the budget items, but one input is invalid
        @Test
        void totalExpenses_setIncomeAnExcessInfo_invalid_expense()
        {
            int income = 2000, rent = 1000, auto = 40, groceries = 100;
            String invalidPersonal = "invalid";
            int totalExpenses = rent + groceries + auto;

            JTextComponentFixture incomeText = window.textBox("incomeField");
            JTextComponentFixture rentText = window.textBox("rentField");
            JTextComponentFixture autoText = window.textBox("autoField");
            JTextComponentFixture groceriesText = window.textBox("groceriesField");
            JTextComponentFixture personalText = window.textBox("personalField");

            incomeText.setText(Integer.toString(income));
            rentText.setText(Integer.toString(rent));
            groceriesText.setText(Integer.toString(groceries));
            personalText.setText(invalidPersonal);
            autoText.setText(Integer.toString(auto));

            JButtonFixture calculateExcessButton = window.button("calculateBut");
            calculateExcessButton.click();

            JLabelFixture expenses = window.label("expensesAnswer");
            JLabelFixture excess = window.label("excessAnswer");

            expenses.requireText("Please ensure all values are numbers");
            excess.requireText("");
        }

        // The excess is calculated from the budget items, but the income is invalid. The result is the same as the
        // last test, but it follows a different code path
        @Test
        void totalExpenses_setIncomeAnExcessInfo_invalid_income()
        {
            int rent = 1000, auto = 40, groceries = 100, personal = 30;
            String invalidIncome = "invalid";
            int totalExpenses = rent + groceries + auto;

            JTextComponentFixture incomeText = window.textBox("incomeField");
            JTextComponentFixture rentText = window.textBox("rentField");
            JTextComponentFixture autoText = window.textBox("autoField");
            JTextComponentFixture groceriesText = window.textBox("groceriesField");
            JTextComponentFixture personalText = window.textBox("personalField");

            incomeText.setText(invalidIncome);
            rentText.setText(Integer.toString(rent));
            groceriesText.setText(Integer.toString(groceries));
            personalText.setText(Integer.toString(personal));
            autoText.setText(Integer.toString(auto));

            JButtonFixture calculateExcessButton = window.button("calculateBut");
            calculateExcessButton.click();

            JLabelFixture expenses = window.label("expensesAnswer");
            JLabelFixture excess = window.label("excessAnswer");

            expenses.requireText("Please ensure all values are numbers");
            excess.requireText("");
        }
    }
}
