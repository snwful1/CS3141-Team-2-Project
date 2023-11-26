/*
 * Author(s): Samuel Wright, Calder Neely, Evan Bradford, Noah Waldorf
 * Created: 10-16-2023
 * Last Updated: 10-26-2023
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Users {
    private ArrayList<User> userList;
    private final String KEY = "abcdefghijklmnop"; // Key *MUST* be 16 bytes long
    private HashMap<String, String> USERS = new HashMap<>(); // <Username, Password>
    private final File usersFile = new File("output/users.txt");

    public Users() {
        userList = new ArrayList<>();
        new File("output").getAbsoluteFile().mkdirs();
        if(!usersFile.exists()) {
            try {
                usersFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("users.txt was not created. ", e);
            }
        }

        if(usersFile.exists()) {
            EncryptionUtils.decrypt(KEY, usersFile);

            Scanner input = null;
            try {
                input = new Scanner(usersFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("users.txt was not found.", e);
            }

            while(input.hasNextLine()) {
                if(input.hasNext()) {
                    String name = input.next();
                    User newUser = new User(name);
                    userList.add(newUser);
                    USERS.put(name, input.next());
                    newUser.initUser();
                    newUser.loadUserData();
                } else
                    break;
            }
            input.close();
            EncryptionUtils.encrypt(KEY, usersFile);
        }
    }

    /**
     * This method writes the contents of the USERS hashmap to an encrypted txt file
     */
    public void save() {
        EncryptionUtils.decrypt(KEY, usersFile);
        try {
            FileWriter myWriter = new FileWriter(usersFile);
            USERS.forEach( (name, password) -> {
                try {
                    myWriter.write(name + " " + password + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("users.txt was not found.", e);
        }
        EncryptionUtils.encrypt(KEY, usersFile);
    }

    /**
     * Creates a new User and inputs it into arraylist and adds username and password to USERS hashmap
     * @param name User's input name
     * @param password User's input password
     * @return The new User
     */
    public User newUser(String name, String password) {
        User user = new User(name);
        userList.add(user);
        USERS.put(name, password);
        return user;
    }

    /**
     * Remove a User from the list
     * @param user The User to remove
     */
    public void removeUser(User user) {
        userList.remove(user);
        // Todo: implement the removal of the users hashMap
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

    public HashMap<String, String> getList() {
        return USERS;
    }

    /**
     * Get the number of users in the Arraylist userList
     * @return The number of users
     */
    public int size() {
        return userList.size();
    }

    /**
     *  Changes username of User and their hashmap
     * @param oldName The old username of User
     * @param newName The new username of User
     */
    public void setName(String oldName, String newName) {
        User user = getUser(oldName);
        if (user != null) {
            userList.remove(user);
            user.setName(newName);
            userList.add(user);
            // Update the username in the USERS HashMap
            USERS.remove(oldName);
            USERS.put(newName, user.getPassword());
        }
    }

    /**
     * Changes password of user's hashmap
     * @param name The username of the User
     * @param newPassword The new password
     */
    public void setPassword(String name, String newPassword) {
        User user = getUser(name);
        if (user != null) {
            user.setPassword(newPassword);
            // Update the password in the USERS HashMap
            USERS.put(name, newPassword);
        }
    }


}
