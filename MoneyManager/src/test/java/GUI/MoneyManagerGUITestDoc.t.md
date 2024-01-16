# Money Manager GUI Test Suite

* Test Case testCategoryInputFieldsAndCalculateButton
  * Enter income 1000
  * Enter rent 500
  * Enter dining 150
  * Click calculate button
  * Expected: excess is income - rent - dining (350) and expenses is rent + dining (650)

* Test Case invalid_income_budgetPage
  * Enter income as a string of non-integers ("invalid")
  * Click calculate button
  * Expected: expenses text box will display error message ("Please ensure all values are numbers")

* Test Case makeGoalString
  * Enter income 2000
  * Enter rent 1000
  * Click calculate button
  * Expected: expenses is rent (1000) and excess is income - rent (1000)
  * Switch to savings goal tab
  * Enter goal 100
  * Enter time 200
  * Enter item "TV"
  * Click calculate button
  * Expected: in the goal table column 0 is item name (TV), column 1 is goal (100), column 2 is excess (1000), column 3 is time (200), column 4 is goal string ("Save $0.33 daily to reach goal")

* Test Case clearGoalFields
  * Switch to savings goal tab
  * CLick clear button
  * Expected: goal text is empty (""), time text is empty (""), item text is empty ("")

* Test Case clearTrackerFields
  * Switch to spending tracker tab
  * CLick clear button
  * Expected: cost text is empty (""), date text is empty date ("MM/DD/YYYY"), item text is empty ("")

* Test Case makeTransactionTable
  * Switch to spending tracker tab
  * Set item to "TV"
  * Set date to "11/11/2022"
  * Set cost to "300"
  * Click add purchase button
  * Expected: in the transaction table column 0 is item name ("TV"), column 1 is cost ("300"), column 2 is date ("11/11/2022"), column 3 is the category ("Rent/Mortgage")

* Test Case checkDateField
  * Switch to spending tracker tab
  * Set date to empty string
  * Expected: date text is default ("MM/DD/YYYY")

* Test Case goalStringUnattainable
  * Set income to 2000
  * Set rent to 1000
  * Click calculate button
  * Expected: excess test is income - rent (1000)
  * Switch to savings goal tab
  * Set goal item to "Car"
  * Set goal to "1000"
  * Set time to "1"
  * Click calculate button
  * Expected: in the goal table column 0 is item name ("Car"), column 1 is goal (1000), column 2 is excess (1000), column 3 is time (1), column 4 is goal string ("Savings goal is unattainable. Try saving $33.33 per day for 30.00 days")
  
* Test Case testInvalidInput
  * Set auto to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set auto to 0
  * Set rent to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set rent to 0
  * Set dining to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set dining to 0
  * Set utilities to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set utilities to 0
  * Set transportation to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set transportation to 0
  * Set entertainment to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set entertainment to 0
  * Set groceries to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set groceries to 0
  * Set debt to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set debt to 0
  * Set savings to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set savings to 0
  * Set investments to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set investments to 0
  * Set medical to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set medical to 0
  * Set personal to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set personal to 0
  * Set other to "invalid"
  * Expected: expenses text is "Please ensure all values are numbers"
  * Set other to 0

* Test case invalidHouseholdSizeSetFoodStampInfo
  * Set household size to 0
  * Set income to 4000
  * Click calculate button
  * Expected: fSEligibleValueText is "Please ensure the household size is larger than zero"

* Test case setTransactionInvalidItem
  * Switch to spending tracker tab
  * Set date to "10/23/2023"
  * Set cost to "300"
  * Click add purchase button
  * Expected: date is default ("MM/DD/YYYY), cost is empty (""), item is empty ("")

* Test case setTransactionInvalidCost
  * Switch to spending tracker tab
  * Set date to "10/23/2023"
  * Set cost to "invalid"
  * Set item to "TV"
  * Click add purchase button
  * Expected: date is default ("MM/DD/YYYY), cost is empty (""), item is empty ("")

* Test case setTransactionInvalidDate
  * Switch to spending tracker tab
  * Set date to "invalid"
  * Set cost to "300"
  * Set item to "TV"
  * Click add purchase button
  * Expected: date is default ("MM/DD/YYYY), cost is empty (""), item is empty ("")

* Test case goal_invalid_time
  * Set income to 2000
  * Click calculate button
  * Switch to savings goal tab
  * Set item to TV
  * Set goal to 300
  * Set time to "invalid"
  * Press calculate button
  * Expected: goal text is empty (""), time text is empty (""), item text is empty ("")