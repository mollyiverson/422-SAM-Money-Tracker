package backend;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Calculates tax rates for items
 */
public class TaxCalculator {
    HashMap<String, Double> salesTaxRates = new HashMap<>();
    HashMap<String, Double> groceryTaxRates = new HashMap<>();

    /**
     * Sets up the tax rate hash maps
     */
    public TaxCalculator() {
        addTaxValues("MoneyManager/salesTax.csv");
    }

    /**
     * Sets up the tax rate hash maps
     * @param pathname the pathname for the tax csv file
     */
    public TaxCalculator(String pathname)
    {
        addTaxValues(pathname);
    }

    public TaxCalculator(boolean isTesting){}


    /**
     * Reads through state sales and grocery tax rates from a csv file and loads tax rate hash maps
     */
    public boolean addTaxValues(String pathname) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(new File(pathname)));
            List<String[]> lines = csvReader.readAll();
            for (String[] line : lines) {
                String state = line[0];
                double salesTax = Double.parseDouble(line[1]);
                double groceriesTax = Double.parseDouble(line[2]);
                salesTaxRates.put(state, salesTax);
                groceryTaxRates.put(state, groceriesTax);
            }
            return true;
        } catch (Exception e) {
            return false;
            //throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the total cost, including tax, of an item based on its category and state
     *
     * @param initCost The initial cost of the item, without tax
     * @param state    The US state the item was purchased in
     * @param category The spending category
     * @return The tax-included total
     */
    public double calculateTotalCost(double initCost, String state, String category) {
        ArrayList<String> categories = new ArrayList<String>(Arrays.asList("Auto", "Utilities", "Transportation", "Groceries", "Entertainment", "Debt Payments", "Savings", "Investments", "Medical", "Dining Out", "Personal Care", "Other"));

        ArrayList<String> salesTaxCategories = new ArrayList<String>(Arrays.asList("Entertainment", "Dining Out", "Personal Care", "Other"));

        if (!categories.contains(category) || !salesTaxRates.containsKey(state)) {
            // Category or state not recognized
            return initCost;
        } else if (salesTaxCategories.contains(category)) {
            double tax = salesTaxRates.get(state) / 100 * initCost;
            return initCost + tax;
        } else if (category.equals("Groceries")) {
            // Some states have lower tax rates for groceries
            double tax = groceryTaxRates.get(state) / 100 * initCost;
            return initCost + tax;
        } else {
            return initCost;
        }
    }

    public void addSalesRate(String key, double value){
        this.salesTaxRates.put(key, value);
    }

    public void addGroceryRate(String key, double value){
        this.groceryTaxRates.put(key, value);
    }
}
