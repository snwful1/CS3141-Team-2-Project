/**
 * @author Pan Prathongkham
 * CS3141-Team Software Project
 * 10/16/2023
 */
public class Income {

    private String name;
    private double amount;
    private int frequencyInDay;

    public Income (String name, double amount, int frequencyInDay){
        this.name = name;
        this.amount = amount;
        this.frequencyInDay = frequencyInDay;
    }

    //getters and setters
    public String getName(){
        return name;
    }

    public double getAmount(){
        return amount;
    }

    public int getFrequencyInDay() {
        return frequencyInDay;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setFrequencyInDay(int frequencyInDay){
        this.frequencyInDay = frequencyInDay;
    }

    // toString method to represent the object as a string
    @Override
    public String toString() {
        return "Income{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", frequencyInDays=" + frequencyInDay +
                '}';
    }

}
