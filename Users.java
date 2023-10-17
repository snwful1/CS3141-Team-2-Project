/*
 * Author(s): Samuel Wright, 
 * Date: 10-16-2023
 *
 * Description: The `Users` class is part of a personal finance management system that facilitates user management
 * and provides essential functionality for handling user data. It acts as a repository for user objects and allows
 * for user addition, removal, retrieval by username, and user count retrieval.
 *
 * Key Features:
 * - Add a user to the list
 * - Remove a user from the list
 * - Retrieve a user by their username
 * - Get the total number of users in the list
 */

import java.util.ArrayList;

public class Users {
    private ArrayList<User> userList;

    public Users() {
        userList = new ArrayList<>();
    }

    // Add a user to the list
    public void addUser(User user) {
        userList.add(user);
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

    public static void main(String[] args) {

    }
}

