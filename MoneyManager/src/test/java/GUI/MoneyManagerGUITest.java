package GUI;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyManagerGUITest {
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


    @Test
    public void testCategoryInputFieldsAndCalculateButton() {
        // Locate and interact with text fields
        JTextComponentFixture incomeText = window.textBox("incomeField");
        JTextComponentFixture rentText = window.textBox("rentField");
        JTextComponentFixture diningText = window.textBox("diningField");

        int income = 1000, rent = 500, dining = 150;

        // Input text into the text fields
        incomeText.setText(Integer.toString(income));
        rentText.setText(Integer.toString(rent));
        diningText.setText(Integer.toString(dining));

        // Locate and click the button
        JButtonFixture calculateButton = window.button("calculateBut");
        calculateButton.click();

        // Locate and assert the result fields
        JLabelFixture expenses = window.label("expensesAnswer");
        JLabelFixture excess = window.label("excessAnswer");

        expenses.requireText((rent + dining) + ".00");
        excess.requireText((income - rent - dining) + ".00");
    }

    @Test
    void invalid_income_budgetPage()
    {
        JTextComponentFixture incomeText = window.textBox("incomeField");
        incomeText.setText("invalid");

        JLabelFixture expenses = window.label("expensesAnswer");
        JLabelFixture excess = window.label("excessAnswer");

        JButtonFixture calculateButton = window.button("calculateBut");
        calculateButton.click();

        expenses.requireText("Please ensure all values are numbers");
        excess.requireText("");
    }




    @Test
    void makeGoalString() {
        JTextComponentFixture incomeText = window.textBox("incomeField");
        JTextComponentFixture rentText = window.textBox("rentField");

        int income = 2000, rent = 1000;

        incomeText.setText(Integer.toString(income));
        rentText.setText(Integer.toString(rent));

        JButtonFixture calculateExcessButton = window.button("calculateBut");
        calculateExcessButton.click();

        JLabelFixture expenses = window.label("expensesAnswer");
        JLabelFixture excess = window.label("excessAnswer");

        expenses.requireText((rent) + ".00");
        excess.requireText((income - rent) + ".00");

        // Now testing the goal page

        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Savings Goal");

        JTextComponentFixture goalText = window.textBox("goalField");
        JTextComponentFixture timeText = window.textBox("timeField");
        JTextComponentFixture itemText = window.textBox("itemField");

        int goal = 100, time = 300;
        String item = "TV";

        goalText.setText(Integer.toString(goal));
        timeText.setText(Integer.toString(time));
        itemText.setText(item);

        JButtonFixture calculateGoalButton = window.button("calculateGoal");
        calculateGoalButton.click();

        // Testing goal table values
        JTableFixture goalTable = window.table("goalTable");

        String actualItemName = goalTable.valueAt(TableCell.row(1).column(0));
        assertEquals(actualItemName, item);

        String actualGoal = goalTable.valueAt(TableCell.row(1).column(1));
        assertEquals(actualGoal, Integer.toString(goal));

        String actualExcess = goalTable.valueAt(TableCell.row(1).column(2));
        assertEquals(actualExcess, (excess.text()));

        String actualTime = goalTable.valueAt(TableCell.row(1).column(3));
        assertEquals(actualTime, Integer.toString(time));

        String actualAdvice = goalTable.valueAt(TableCell.row(1).column(4));
        assertEquals(actualAdvice, "Save $0.33 daily to reach goal");
    }

    @Test
    void clearGoalFields() {
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Savings Goal");
        JButtonFixture clearGoalButton = window.button("goalClear");
        clearGoalButton.click();
        JTextComponentFixture goalText = window.textBox("goalField");
        JTextComponentFixture timeText = window.textBox("timeField");
        JTextComponentFixture itemText = window.textBox("itemField");
        assertEquals(goalText.text(), "");
        assertEquals(timeText.text(), "");
        assertEquals(itemText.text(), "");
    }

    @Test
    void clearTrackerFields(){
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");
        JButtonFixture clearButton = window.button("clearTracker");
        clearButton.click();
        JTextComponentFixture dateText = window.textBox("dateField");
        JTextComponentFixture costText = window.textBox("costField");
        JTextComponentFixture itemText = window.textBox("itemNameField");

        assertEquals(costText.text(), "");
        assertEquals(dateText.text(), "MM/DD/YYYY");
        assertEquals(itemText.text(), "");
    }




    @Test
    void makeTransactionTable() {

        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");

        JComboBoxFixture categoryOptions = window.comboBox("categoryOptions");
        JTextComponentFixture dateText = window.textBox("dateField");
        JTextComponentFixture costText = window.textBox("costField");
        JTextComponentFixture itemText = window.textBox("itemNameField");


        String item = "TV", date = "11/11/2022", cost = "300";

        String selectedCategory = categoryOptions.selectedItem();
        dateText.setText(date);
        costText.setText(cost);
        itemText.setText(item);

        JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
        addPurchaseButton.click();

        // Testing goal table values
        JTableFixture transactionTable = window.table("transactionTable");

        String actualItemName = transactionTable.valueAt(TableCell.row(1).column(0));
        assertEquals(actualItemName, item);

        String actualCost = transactionTable.valueAt(TableCell.row(1).column(1));
        assertEquals(actualCost, cost);

        String actualDate = transactionTable.valueAt(TableCell.row(1).column(2));
        assertEquals(actualDate, date);

        String actualCategory = transactionTable.valueAt(TableCell.row(1).column(3));
        assertEquals(actualCategory, selectedCategory);
    }

    @Test
    void checkDateField() {
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");
        JTextComponentFixture dateText = window.textBox("dateField");
        dateText.setText("");
        assertTrue(dateText.text().isEmpty());
    }

    @Test
    void goalStringUnattainable(){
        JTextComponentFixture incomeText = window.textBox("incomeField");
        JTextComponentFixture rentText = window.textBox("rentField");

        int income = 2000, rent = 1000;

        incomeText.setText(Integer.toString(income));
        rentText.setText(Integer.toString(rent));

        JButtonFixture calculateExcessButton = window.button("calculateBut");
        calculateExcessButton.click();

        JLabelFixture excess = window.label("excessAnswer");

        excess.requireText((income - rent) + ".00");

        // Now testing the goal page

        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Savings Goal");

        JTextComponentFixture goalText = window.textBox("goalField");
        JTextComponentFixture timeText = window.textBox("timeField");
        JTextComponentFixture itemText = window.textBox("itemField");
        JTableFixture goalTable = window.table("goalTable");

        String item = "Car", goal = "1000", time = "1";
        goalText.setText(goal);
        timeText.setText(time);
        itemText.setText(item);

        JButtonFixture calculateGoalButton = window.button("calculateGoal");
        calculateGoalButton.click();

        String actualItemName = goalTable.valueAt(TableCell.row(1).column(0));
        assertEquals(actualItemName, item);

        String actualGoal = goalTable.valueAt(TableCell.row(1).column(1));
        assertEquals(actualGoal, goal);

        String actualExcess = goalTable.valueAt(TableCell.row(1).column(2));
        assertEquals(actualExcess, (excess.text()));

        String actualTime = goalTable.valueAt(TableCell.row(1).column(3));
        assertEquals(actualTime, time);

        String actualAdvice = goalTable.valueAt(TableCell.row(1).column(4));
        assertEquals(actualAdvice, "Savings goal is unattainable. Try saving $33.33 per day for 30.00 days");
    }

    @Test
    void testInvalidInput(){
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


        JLabelFixture expensesAnswer = window.label("expensesAnswer");
        JButtonFixture calculateButton = window.button("calculateBut");
        String expectedExpensesAnswer = "Please ensure all values are numbers";

        String invalid = "invalid";

        incomeText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        incomeText.setText("100");

        autoText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        autoText.setText("0");

        rentText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        rentText.setText("0");

        diningText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        diningText.setText("0");

        utilitiesText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        utilitiesText.setText("0");

        transportationText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        transportationText.setText("0");

        entertainmentText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        entertainmentText.setText("0");

        groceriesText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        groceriesText.setText("0");

        debtText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        debtText.setText("0");

        savingsText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        savingsText.setText("0");

        investmentsText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        investmentsText.setText("0");

        medicalText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        medicalText.setText("0");

        personalText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        personalText.setText("0");

        otherText.setText(invalid);
        calculateButton.click();
        assertEquals(expectedExpensesAnswer, expensesAnswer.text());
        otherText.setText("0");
    }

    @Test
    void invalidHouseholdSizeSetFoodStampInfo(){
        JTextComponentFixture householdText = window.textBox("householdSizeValue");
        householdText.setText("0");
        JTextComponentFixture incomeText = window.textBox("incomeField");
        JLabelFixture fSEligibleValue = window.label("fSEligibleValue");

        String income = "4000";
        incomeText.setText(income);

        JButtonFixture calculateButton = window.button("calculateBut");
        calculateButton.click();

        String fSEligibleValueAnswerText = fSEligibleValue.text();
        String fSEligibleValueExpensesAnswer = "Please ensure the household size is larger than zero";
        assertEquals(fSEligibleValueExpensesAnswer, fSEligibleValueAnswerText);
    }

    @Test
    void setTransactionInvalidItem(){
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");
        JTextComponentFixture dateText = window.textBox("dateField");
        JTextComponentFixture costText = window.textBox("costField");
        JTextComponentFixture itemText = window.textBox("itemNameField");

        String date = "10/23/2023", cost = "300";

        dateText.setText(date);
        costText.setText(cost);

        JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
        addPurchaseButton.click();

        assertEquals(dateText.text(), "MM/DD/YYYY");
        assertEquals(costText.text(), "");
        assertEquals(itemText.text(), "");
    }

    @Test
    void setTransactionInvalidCost(){
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");
        JTextComponentFixture dateText = window.textBox("dateField");
        JTextComponentFixture costText = window.textBox("costField");
        JTextComponentFixture itemText = window.textBox("itemNameField");

        String date = "10/23/2023", cost = "invalid", item = "TV";

        dateText.setText(date);
        costText.setText(cost);
        itemText.setText(item);

        JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
        addPurchaseButton.click();

        assertEquals(dateText.text(), "MM/DD/YYYY");
        assertEquals(costText.text(), "");
        assertEquals(itemText.text(), "");
    }

    @Test
    void setTransactionInvalidDate(){
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Spending Tracker");

        JTextComponentFixture dateText = window.textBox("dateField");
        JTextComponentFixture costText = window.textBox("costField");
        JTextComponentFixture itemText = window.textBox("itemNameField");

        String date = "invalid", cost = "300", item = "TV";

        dateText.setText(date);
        costText.setText(cost);
        itemText.setText(item);

        JButtonFixture addPurchaseButton = window.button("addPurchaseButton");
        addPurchaseButton.click();

        assertEquals(dateText.text(), "MM/DD/YYYY");
        assertEquals(costText.text(), "");
        assertEquals(itemText.text(), "");
    }
    
    @Test
    void goal_invalid_time()
    {
        JTextComponentFixture incomeText = window.textBox("incomeField");
        int income = 2000;
        incomeText.setText(Integer.toString(income));
        JButtonFixture calculateExcessButton = window.button("calculateBut");
        calculateExcessButton.click();
        JTabbedPaneFixture tabbedPane = window.tabbedPane("tabbedPane");
        tabbedPane.selectTab("Savings Goal");

        JTextComponentFixture goalText = window.textBox("goalField");
        JTextComponentFixture timeText = window.textBox("timeField");
        JTextComponentFixture itemText = window.textBox("itemField");

        String item = "TV", goal = "300", time = "invalid";
        goalText.setText(goal);
        timeText.setText(time);
        itemText.setText(item);

        JButtonFixture calculateGoalButton = window.button("calculateGoal");
        calculateGoalButton.click();

        goalText.requireText("");
        timeText.requireText("");
        itemText.requireText("");
    }

}