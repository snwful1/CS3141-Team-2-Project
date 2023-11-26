/*
 * Author(s): Samuel Wright, Calder Neely, Evan Bradford, Noah Waldorf, Pan Prathongkham
 * Created: 10-12-2023
 * Last edited: 11-26-2023
 */

import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {
    private String name;
    private String password;
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;
    private String phoneNumber;
    private String email;
    private double balance;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        incomeList = new ArrayList<Income>();
        expenseList = new ArrayList<Expense>();
        initUser();
    }

    public User(String name) {
        this.name = name;
        incomeList = new ArrayList<Income>();
        expenseList = new ArrayList<Expense>();
        initUser();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //getter and setter for email
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void changeName(String newName) { this.name = newName;}

    public void changeUsername(String newUsername) {
        this.name = newUsername;
        saveUserData();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        saveUserData();
    }

    private void saveUserData() {
        File userFile = new File("output/userdata/" + name + ".txt");

        try {
            FileWriter myWriter = new FileWriter(userFile, false);
            myWriter.write(this.password + "\n"); // Save the new password
            for (Income i : incomeList) {
                myWriter.write("Income: " + i.getName() + " " + i.getAmount() + " " + i.getFrequencyInDays() + "\n");
            }
            for (Expense e : expenseList) {
                myWriter.write("Expense: " + e.getName() + " " + e.getAmount() + " " + e.getFrequencyInDays() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write user data");
            e.printStackTrace();
        }
    }

    public void initUser() {
        File userFile = new File("output/userdata/"+name + ".txt");
        File userDir = new File("output/userdata");

        //Create file if it does not exist and write all incomes and expenses
        try {
            userDir.getAbsoluteFile().mkdirs();
            userFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed to create user file");
            e.printStackTrace();
        }
    }

    public void addIncome(Income i) {
        incomeList.add(i);
    }

    public void addExpense(Expense e) {
        expenseList.add(e);
    }

    public void addNewIncome(Income i) {
        incomeList.add(i);

        //Add income to user file
        try {
            FileWriter myWriter = new FileWriter("output/userdata/"+name + ".txt", true);
            myWriter.write("Income: "  + i.getName() + " " + i.getAmount() + " " + i.getFrequencyInDays() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write user data");
            e.printStackTrace();
        }
    }

    public void addNewExpense(Expense e) {
        expenseList.add(e);

        //Add expense to user file
        try {
            FileWriter myWriter = new FileWriter("output/userdata/"+name + ".txt", true);
            myWriter.write("Expense: "  + e.getName() + " " + e.getAmount() + " " + e.getFrequencyInDays() + "\n");
            myWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write user data");
            err.printStackTrace();
        }
    }

    public void addNewBalance(double b) {
        //Add expense to user file
        try {
            FileWriter myWriter = new FileWriter("output/userdata/"+name + ".txt", true);
            myWriter.write(b + "\n");
            myWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write user data");
            err.printStackTrace();
        }
    }

    public void loadUserData() {
        File usersFile = new File("output/userdata/"+name + ".txt");
        Scanner input = null;



        //Create file if it does not exist and open with scanner
        try {
            usersFile.createNewFile();
            input = new Scanner(usersFile);
        } catch (IOException e) {
            System.out.println("Failed to load users' data");
            e.printStackTrace();
        }
        //Read in user id, name, and password and add user to the list
        String type = null;
        String name = null;
        double amount = 0;
        int frequency = 0;

        if (input.hasNext()) {
            balance = input.nextDouble();
        }

        while (input.hasNextLine()) {
            if (input.hasNext()) {
                type = input.next();
                name = input.next();
                amount = input.nextDouble();
                frequency = input.nextInt();
                if (type.equals("Income:")) {
                    addIncome(new Income(name, amount, frequency));
                } else if (type.equals("Expense:")) {
                    addExpense(new Expense(name, amount, frequency));
                }
            } else {break;}
        }
        input.close();
    }
}
