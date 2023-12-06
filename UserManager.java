/*
 * Author(s): Samuel Wright, Calder Neely, Evan Bradford, Noah Waldorf
 * Created: 10-16-2023
 * Last Updated: 10-26-2023
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserManager {
    private static ArrayList<User> userList;
    private final String KEY = "abcdefghijklmnop"; // Key *MUST* be 16 bytes long
    private static HashMap<User, File> userFiles = new HashMap<>(); // Each user will be mapped to its own file.

    //Todo: Implement encryption in both the constructor and the save() method.
    public UserManager() {
        userList = new ArrayList<>();
        File userData = new File("resources/userdata");
        if(!userData.exists())
            userData.getAbsoluteFile().mkdirs();

        if(userData.exists()) {
            File users[] = userData.listFiles();
            for (File userFile : users) {
                System.out.println(userFile.getName());

                Scanner input = null;
                try {
                    input = new Scanner(userFile);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(userFile.getName() + ".txt was not found.", e);
                }
                String username = "";
                String password = "";
                double balance = 0.0;

                // Todo: email needs to be properly implemented

                if (input.hasNextLine())
                    username = input.nextLine();
                if (input.hasNextLine())
                    password = input.nextLine();
                User user = newUser(username, password, userFile);
                //if(input.hasNextLine())
                  //user.setEmail(input.nextLine());

                if (input.hasNextLine())
                    user.setBalance(Double.parseDouble(input.nextLine()));

                String type = null;
                String name = null;
                double amount = 0;
                int frequency = 0;
                while(input.hasNextLine()) {
                    if (input.hasNext()) {
                        type = input.next();
                        name = input.next();
                        amount = input.nextDouble();
                        frequency = input.nextInt();
                        if (type.equals("Income:")) {
                            user.addIncome(new Income(name, amount, frequency));
                        } else if (type.equals("Expense:")) {
                            user.addExpense(new Expense(name, amount, frequency));
                        }
                    } else {break;}
                }

                System.out.println("username: "+ username);
                System.out.println("password: "+ password);

                input.close();
            }
        }
    }

    /**
     * This method writes all user data to its own encrypted file.
     */
    public void save() {
        for (User user : userList) {
            try {
                File file = userFiles.get(user);
                //EncryptionUtils.decrypt(KEY, file);
                FileWriter w = new FileWriter(file);
                w.write(user.getName() + "\n");
                w.write(user.getPassword() + "\n");
                //w.write(user.getEmail() + "\n"); //Todo: Implement
                w.write(user.getBalance() + "\n");
                user.getIncomeList().forEach( income -> {
                    try {
                        w.write("Income: "  + income.getName() + " " + income.getAmount() + " " + income.getFrequencyInDays() + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write user income", e);
                    }
                });
                user.getExpenseList().forEach(expense -> {
                    try {
                        w.write("Expense: "  + expense.getName() + " " + expense.getAmount() + " " + expense.getFrequencyInDays() + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write user expense", e);
                    }
                });
                w.close();
                //xEncryptionUtils.encrypt(KEY, file);
            } catch (IOException e) {
                throw new RuntimeException(user.getName() + ".txt was not found", e);
            }

        }
    }

    /**
     * Creates a new User and inputs it into arraylist and adds username and password to USERS hashmap
     * @param name User's input name
     * @param password User's input password
     * @param f The users file. Brand-new users should have a NULL file
     */
    public User newUser(String name, String password, File f) { // Todo: add email
        User user = new User(name, password);
        userList.add(user);
        if(f == null) {
            File file = new File("resources/userdata/"+name+".txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(name+".txt was not created!",e);
            }
            userFiles.put(user, file);
        } else
            userFiles.put(user, f);
        return user;
    }

    /**
     * Remove a User from the list
     * @param user The User to remove
     */
    public void removeUser(User user) {
        userList.remove(user);
    }

    /**
     * Get a User by username
     * @param username The username of the User
     * @return The User that has matching username
     */
    public User getUser(String username) {
        for (User user : userList) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    /**
     * Get the number of users in the Arraylist userList
     * @return The number of users
     */
    public int size() {
        return userList.size();
    }
}
