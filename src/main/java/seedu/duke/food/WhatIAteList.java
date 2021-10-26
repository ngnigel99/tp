package seedu.duke.food;

import seedu.duke.ui.Ui;

import java.util.ArrayList;

/**
 * Represents a list of what the user has eaten.
 *  This would include the date of the record and
 *      the food items consumed.
 *
 * @author ngnigel99
 */
public class WhatIAteList  {

    private String stallName;
    private ArrayList<FoodRecord> whatIAteList = new ArrayList<>();

    /**
     * No stallName - for the User
     */
    public WhatIAteList() {

    }

    /**
     * For a reference list
     * @param stallName name of a stall
     */
    public WhatIAteList(String stallName) {
        this.stallName = stallName;
    }

    public ArrayList<FoodRecord> getList() {
        return whatIAteList;
    }

    /**
     * For reference list
     * @param whatIAteList loaded reference list
     */
    public void setWhatIAteList(ArrayList<FoodRecord> whatIAteList) {
        this.whatIAteList = whatIAteList;
    }

    /**
     * Adds a food Record to a list, then notifies the user of what  item
     *  they  have entered.
     *
     * @author  ngnigel99
     * @param recordToAdd record to add to list
     * @param isSilent checks whether to print Ui message
     */
    public void addToList(FoodRecord recordToAdd, boolean isSilent) {
        whatIAteList.add(recordToAdd);
        if (!isSilent) {
            Ui.printAddRecord(recordToAdd);

        }
    }

    /**
     * Prints  index with suffix for visuals.
     *
     * @author ngnigel99
     * @param index index of list
     */
    public void printIndexWithSuffix(int index) {
        String[] suffixList = {"st", "nd", "rd", "th"};
        String suffixToPrint;
        int lastDigit = index % 10;
        int exceptionTeen = index % 100; //11,12,13th
        if (lastDigit == 1 && exceptionTeen != 11) {
            suffixToPrint = suffixList[0];
        } else if (lastDigit == 2 && exceptionTeen != 12) {
            suffixToPrint = suffixList[1];
        } else if (lastDigit == 3 && exceptionTeen != 13) {
            suffixToPrint = suffixList[2];
        } else {
            suffixToPrint = suffixList[3];
        }
        System.out.print(index + suffixToPrint  +  ",");
    }

    /**
     * Prints food records collated in list.
     * In context, for today.
     *
     * @author ngnigel99
     * @param withMessages if messages are to be included
     */
    public void printList(boolean withMessages) {
        int index = 1;  //TODO integrate this with storage so it's not a magic number
        int calorieSum =  0;
        for (FoodRecord listRecord : whatIAteList) {
            printIndexWithSuffix(index);
            if (withMessages) {
                System.out.println("You consumed  " + listRecord.getFoodName()
                        + " , which has a calorie count of : "
                        + listRecord.getCalorieCount()
                        + ((listRecord.getDateIAte() != null) ? " on " + listRecord.getDateIAte() + "!" : "!"));
            } else {
                System.out.println(listRecord.getFoodName() + " : " + listRecord.getCalorieCount() + "Kcal");
            }
            index++;
            calorieSum += listRecord.getCalorieCount();
        }
        System.out.println("Wow, that's a lot of food! Finished reading the list");
        if (withMessages) {
            System.out.println("You consumed " + calorieSum + " calories in total today!");
        }
    }

    public void clearList() {
        whatIAteList.clear();
        Ui.printDoneClearList();
    }
}
