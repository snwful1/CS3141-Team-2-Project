import java.util.ArrayList;

public class User {
  //private int userID;
  private String name;
  private String password;

  public User(String name, String password) {
      //this.userID = userID;
      this.name = name;
      this.password = password;
  }

 // public int getUserID() {
      //return userID;
  //}

  public String getName() {
      return name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public void changeName(String newName) {
    this.name = newName;
  }
}
