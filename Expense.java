/*
*@author Evan Bradford
* CS 3141
* 10/11/2023
* A class representing recurring expenses
 */
public class Expense{

    private String name; // name of expense
    private int cost; // how much the expense is
    private int days; // how often it recurs in days

    // Standard Constructor
    public Expense(String name, int cost, int days){
        this.name = name;
        this.cost = cost;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }


}