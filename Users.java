/*
 * Author(s): Samuel Wright,
 * Created: 10-16-2023
 * Last Updated: 10-22-2023
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private ArrayList<User> userList;

    public Users() {
        userList = new ArrayList<>();
    }

    // Add a user to the list
    public void addUser(User user) {
        userList.add(user);
    }

    public void addNewUser(User user) {
        userList.add(user);

        //Write user login to file
        try {
            FileWriter myWriter = new FileWriter("users.txt", true);
            myWriter.write("\n" + user.getName() + " " + user.getPassword());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write user data");
            e.printStackTrace();
        }
    }

    // Remove a user from the list
    public void removeUser(User user) {
        userList.remove(user);
    }

    // Get a user by username
    public User getUser(String username) {
        for (User user : userList) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Get the number of users in the list
    public int size() {
        return userList.size();
    }

    //Read in user logins from text document
    public void loadData() {
        //I could not get the mkdirs() to actually create the directory
        //File usersFile = new File("/bin/users.txt");
        File usersFile = new File("users.txt");
        File binDir = new File("/bin");
        Scanner input = null;

        //Create file if it does not exist and open with scanner
        try {
            binDir.mkdirs();
            usersFile.createNewFile();
            input = new Scanner(usersFile);
        } catch (IOException e) {
            System.out.println("Failed to load users' data");
            e.printStackTrace();
        }
        //Read in user id, name, and password and add user to the list
        String name = null;
        String password = null;
        while (input.hasNextLine()) {
            if (input.hasNext()) {
                name = input.next();
                password = input.next();
                User newUser = new User(name, password);
                addUser(newUser);
                newUser.initUser();
                newUser.loadUserData();
            } else {break;}
        }
        input.close();
    }
}
