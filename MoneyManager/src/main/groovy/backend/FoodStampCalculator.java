package backend;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class FoodStampCalculator {

    private HashMap<String, Double> foodStampRates = new HashMap<>();
    private final int FEDERAL_POVERTY_LEVEL_BASE = 14580;
    private final int ALASKA_POVERTY_LEVEL_BASE = 18210;
    private final int HAWAII_POVERTY_LEVEL_BASE = 16770;
    private final int FEDERAL_POVERTY_LEVEL_EXTRA = 5140;
    private final int ALASKA_POVERTY_LEVEL_EXTRA = 6430;
    private final int HAWAII_POVERTY_LEVEL_EXTRA = 5910;

    /**
     * Creates new instance of FoodStampCalculator
     */
    public FoodStampCalculator(){
        addFoodStampRates("MoneyManager/FoodStampsRate.csv");
    }

    public FoodStampCalculator(String pathname){
        addFoodStampRates(pathname);
    }
    public FoodStampCalculator(boolean forTesting){
    }

    public HashMap<String, Double> getFoodStampRates() {
        return foodStampRates;
    }

    /**
     * Populates the hashtable with states and the percentage of the poverty level
     * that makes one eligible for food stamps.
     * @param pathname the path to the file that the states and their rates are found.
     * @return true if the rates are added successfullly and false if and they weren't
     */
    public boolean addFoodStampRates(String pathname){
        try{
            CSVReader reader = new CSVReader(new FileReader(new File(pathname)));
            List<String[]> lines = reader.readAll();
            for(String[] line : lines){
                foodStampRates.put(line[0], Double.parseDouble(line[1]));
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Checks if the user is eligible for food stamps or not based on the household size, the state, and the monthly income.
     * @param state The state where the user is from.
     * @param income The monthly income of the user.
     * @param household The number of people in the household.
     * @return True if the user is eligible for food stamps, false if they are not.
     */
    public boolean eligibleForFoodStamps(String state, double income, int household) {
        if(this.foodStampRates.containsKey(state)){
            double rate = this.foodStampRates.get(state);
            if(state.equals("AK")){
                return income <= ((double) (((household - 1) * ALASKA_POVERTY_LEVEL_EXTRA) + ALASKA_POVERTY_LEVEL_BASE) / 12) * rate;
            }else if(state.equals("HI")){
                return income <= ((double) (((household - 1) * HAWAII_POVERTY_LEVEL_EXTRA) + HAWAII_POVERTY_LEVEL_BASE) / 12) * rate;
            }else{
                return income <= ((double) (((household - 1) * FEDERAL_POVERTY_LEVEL_EXTRA) + FEDERAL_POVERTY_LEVEL_BASE) / 12) * rate;
            }
        }
        return false;
    }

    public void addRate(String key, double value){
        this.foodStampRates.put(key, value);
    }
}
