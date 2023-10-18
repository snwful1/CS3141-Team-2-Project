/*
*@author Evan Bradford
* CS 3141
* 10/11/2023
* A class representing recurring expenses
 */
public class Expense{

    private String name; // name of expense
    private double amount; // how much the expense is
    private int frequencyInDays; // how often it recurs in days

    // Standard Constructor
    public Expense(String name, double amount, int frequencyInDays){
        this.name = name;
        this.amount = amount;
        this.frequencyInDays = frequencyInDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void getAmount(double amount) {
        this.amount = amount;
    }

    public int getFrequencyInDays() {
        return frequencyInDays;
    }

    public void setFrequencyInDays(int frequencyInDays) {
        this.frequencyInDays = frequencyInDays;
    }


}