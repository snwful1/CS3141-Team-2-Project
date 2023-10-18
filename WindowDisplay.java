import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;


public class WindowDisplay extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Users userList = initUsers();
		User currentUser = null;

		primaryStage.setTitle("Financial Assistant");


		//Get the primary screen
		Screen screen = Screen.getPrimary();

		// Set the stage dimensions to match the screen dimensions
		primaryStage.setX(screen.getVisualBounds().getMinX());
		primaryStage.setY(screen.getVisualBounds().getMinY());
		primaryStage.setWidth(screen.getVisualBounds().getWidth());
		primaryStage.setHeight(screen.getVisualBounds().getHeight());

		// Create your JavaFX content (e.g., a scene with a layout)

		// Grid Setup
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// User Creator Labels and Textfields
		Text userCreation = new Text("Create User");
		userCreation.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid.add(userCreation, 0, 0, 2, 1);

		Label userName = new Label("New Username:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("New Password:");
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		// User Creator Button
		Button b1 = new Button("Create New User");
		HBox h1 = new HBox(10);
		h1.setAlignment(Pos.CENTER_RIGHT);
		h1.getChildren().add(b1);
		grid.add(h1, 1, 3);

		Text createError = new Text();
		grid.add(createError, 1,4);
		createError.setFill(Color.DARKRED);

		// Button Code
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int createUserResult = createNewUser(userList, userTextField.getText(), pwBox.getText());
				if (createUserResult == 0) {
					createError.setText("New user " + userTextField.getText() + " created!");
					createError.setFill(Color.DARKGREEN);
					userTextField.clear();
					pwBox.clear();
				}
				if (createUserResult == 1){
					createError.setText("Error: Password Needed");
					createError.setFill(Color.DARKRED);
				} else if (createUserResult == 2) {
					createError.setText("Error: Username Needed");
					createError.setFill(Color.DARKRED);
				} else if (createUserResult == 3) {
					createError.setText("Error: User with that name already exists");
					createError.setFill(Color.DARKRED);
				}
			}
		});

		// Login Labels and Textfields
		Text loginText = new Text("Login");
		loginText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid.add(loginText, 0, 5, 2, 1);

		Label userName2 = new Label("Username:");
		grid.add(userName2, 0, 6);
		TextField userTextField2 = new TextField();
		grid.add(userTextField2, 1, 6);

		Label pw2 = new Label("Password:");
		grid.add(pw2, 0, 7);
		PasswordField pwBox2 = new PasswordField();
		grid.add(pwBox2, 1, 7);

		// Login Button
		Button b2 = new Button("Login");
		HBox h2 = new HBox(10);
		h2.setAlignment(Pos.CENTER_RIGHT);
		h2.getChildren().add(b2);
		grid.add(h2, 1, 8);

		Text loginError = new Text();
		grid.add(loginError, 1,9);

		// Button Code
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//return 0 means incorrect username
				//return 1 means incorrect password
				//return 2 means successful login
				int loginOutcome = attemptLogin(userList, currentUser, userTextField2.getText(), pwBox2.getText());
				if(loginOutcome == 0){
					loginError.setText("Error: User does not exist");
					loginError.setFill(Color.DARKRED);
					return;
				}
				if(loginOutcome == 1){
					loginError.setText("Error: Password Incorrect");
					loginError.setFill(Color.DARKRED);
					return;
				}
				if(loginOutcome == 2){
					loginError.setText("Login Successful!");
					loginError.setFill(Color.DARKGREEN);
					return;
				}
			}
		});


		// Commented out while working on ui
		//Group root = new Group(grid);

		Scene scene = new Scene(grid,250, 500);
		// Add your content to the scene

		//Set the scene on the stage and show the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//Initializes users list and reads in saved user data
	private Users initUsers()
	{
		//Initialize Users
		Users users = new Users();

		//Read in user data from text document to load login information, etc
		users.loadData();

		return users;
	}

	//Creates new user with given name and password
	//return 0 means successful user creation
	//return 1 means no username provided
	//return 2 means no password provided
	//return 3 means username already taken
	private int createNewUser(Users users, String name, String pw)
	{
		if ((users.getUser(name) == null) && (name.length() > 0) && (pw.length() > 0)) {
			users.addNewUser(new User(name, pw));
			return 0;
		} else if (name.length() == 0){
			return 1;
		} else if (pw.length() == 0) {
			return 2;
		}
		else {
			return 3;
		}
	}

	//Attempts to login with given credentials
	//return 0 means incorrect username
	//return 1 means incorrect password
	//return 2 means successful login
	private int attemptLogin(Users users, User currentUser, String name, String password)
	{
		//find
		User target = users.getUser(name);
		if (target != null) {
			if (target.getPassword().equals(password)) {
				currentUser = target;
				currentUser.addNewIncome(new Income("test", 101.11, 14));
				currentUser.addNewExpense(new Expense("test", 100.50, 7));
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}
}
