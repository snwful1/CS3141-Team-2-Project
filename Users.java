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

    // Get a user by userID
    public User getUser(int userID) {
        for (User user : userList) {
            if (user.getUserID() == userID) {
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
