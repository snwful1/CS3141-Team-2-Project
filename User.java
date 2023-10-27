/*
 * Author(s): Samuel Wright, Evan Bradford
 * Last Updated: 10-22-2023
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

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        incomeList = new ArrayList<Income>();
        expenseList = new ArrayList<Expense>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void initUser() {
        File userFile = new File(name + ".txt");
        File userDir = new File("/bin/userdata");

        //Create file if it does not exist and write all incomes and expenses
        try {
            userDir.mkdirs();
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
            FileWriter myWriter = new FileWriter(name + ".txt", true);
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
            FileWriter myWriter = new FileWriter(name + ".txt", true);
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
            FileWriter myWriter = new FileWriter(name + ".txt", true);
            myWriter.write(b + "\n");
            myWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write user data");
            err.printStackTrace();
        }
    }

    public void loadUserData() {
        File usersFile = new File(name + ".txt");
        //File usersFileDecyrpt = new File(name + ".decrypted");
        //EncryptionUtils.decyrpt("", usersFile, usersFileDecyrpt);
        Scanner input = null;



        //Create file if it does not exist and open with scanner
        try {
            usersFile.createNewFile();
            input = new Scanner(usersFile);//Decyrpt);
        } catch (IOException e) {
            System.out.println("Failed to load users' data");
            e.printStackTrace();
        }
        //Read in user id, name, and password and add user to the list
        String type = null;
        String name = null;
        double amount = 0;
        int frequency = 0;
        double balance = 0;

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
        //EncryptionUtils.encrypt("", usersFileDecyrpt, usersFile);
    }
}
