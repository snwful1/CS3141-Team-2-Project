/*
 * Author(s): Danyel Munson, Evan Bradford, Calder Neely, Noah Waldorf
 * Created: 10-11-2023
 * Last Updated: 10-23-2023
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class WindowDisplay extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Screen screen = null;
	private	Users userList = null;
	private User currentUser = null;

	// Font Sizes
	int bigText = 60;
	int smallText = 20;

	@Override
	public void start(Stage primaryStage) throws Exception {

		userList = new Users();

		primaryStage.setTitle("Financial Assistant");

		//Get the primary screen
		screen = Screen.getPrimary(); // Changed screen to be a private variable and accessible by all method in this class

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
		grid.setPadding(new Insets(50, 25, 25, 25));

		// User Creator Labels and Textfields
		Text userCreation = new Text("  Create User");
		userCreation.setFont(Font.font("Cambria", FontWeight.BOLD, bigText));
		grid.add(userCreation, 0, 1, 2, 1);

		Label userName = new Label("New Username:");
		grid.add(userName, 0, 2);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 2);

		Label pw = new Label("New Password:");
		grid.add(pw, 0, 3);
		PasswordField pwBox = new PasswordField();
		pwBox.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(pwBox, 1, 3);

		// User Creator Button
		Button b1 = new Button("Create New User");
		HBox h1 = new HBox(10);
		h1.setAlignment(Pos.CENTER);
		h1.getChildren().add(b1);
		grid.add(h1, 1, 4);

		Text createError = new Text();
		createError.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(createError, 1,5);
		createError.setFill(Color.DARKRED);

		// Button Code
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int createUserResult = createNewUser(userList, userTextField.getText(), pwBox.getText());
				if (createUserResult == 0) {
					createError.setText("New user " + userTextField.getText() + " created!");
					createError.setFill(Color.DARKBLUE);
					userTextField.clear();
					pwBox.clear();
				}
				if (createUserResult == 1){
					createError.setText("Error: Username Needed");
					createError.setFill(Color.DARKRED);
				} else if (createUserResult == 2) {
					createError.setText("Error: Password Needed");
					createError.setFill(Color.DARKRED);
				} else if (createUserResult == 3) {
					createError.setText("Error: User with that name already exists");
					createError.setFill(Color.DARKRED);
				}
			}
		});

		// Login Labels and Textfields
		Text loginText = new Text("        Login");
		loginText.setFont(Font.font("Cambria", FontWeight.BOLD, bigText));
		grid.add(loginText, 0, 6, 2, 1);

		Label userName2 = new Label("        Username:");
		grid.add(userName2, 0, 7);
		TextField userTextField2 = new TextField();
		grid.add(userTextField2, 1, 7);

		Label pw2 = new Label("         Password:");
		grid.add(pw2, 0, 8);
		PasswordField pwBox2 = new PasswordField();
		pwBox2.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(pwBox2, 1, 8);

		// Login Button
		Button b2 = new Button("Login");
		HBox h2 = new HBox(10);
		h2.setAlignment(Pos.CENTER_RIGHT);
		h2.getChildren().add(b2);
		grid.add(h2, 1, 9);

		Text loginError = new Text();
		loginError.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(loginError, 1,10);

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
					loadAccountDetails(currentUser);
					((Node) (event.getSource())).getScene().getWindow().hide();
				}
			}
		});



		// Commented out while working on ui
		// Group root = new Group(grid);

		Scene scene = new Scene(grid, 250, 500);
		// Add your content to the scene

		//Set the scene on the stage and show the stage
		primaryStage.setScene(scene);
		scene.getStylesheets().add(WindowDisplay.class.getResource("styleSheets/style.css").toExternalForm());
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		userList.save();
	}

	//Method that loads the account details tab
	private void loadAccountDetails(User currentUser) {
		Stage accountStage = new Stage();
		accountStage.setTitle("Financial Assistant");
		accountStage.setX(screen.getVisualBounds().getMinX());
		accountStage.setY(screen.getVisualBounds().getMinY());
		accountStage.setWidth(screen.getVisualBounds().getWidth());
		accountStage.setHeight(screen.getVisualBounds().getHeight());

		// Account details grid
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.TOP_LEFT);
		grid2.setHgap(5);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));

		Text userDetails = new Text("Account Details");
		userDetails.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid2.add(userDetails, 0, 0, 2, 1);

		Label username = new Label("Username: " +currentUser.getName());
		grid2.add(username, 0, 1);

		Label password = new Label("Password: " +userList.getList().get(currentUser.getName()));
		grid2.add(password, 0, 2);
		Button b3 = new Button("Change Password");
		HBox h3 = new HBox(10);
		h3.setAlignment(Pos.CENTER_RIGHT);
		h3.getChildren().add(b3);
		grid2.add(h3, 1, 2);

		Label email = new Label("Email:");
		//grid2.add(email, 0, 3);

		Label phoneNumber = new Label("Phone Number:");
		//grid2.add(phoneNumber, 0, 4);

		// Financial info grid
		GridPane grid3 = new GridPane();
		grid3.setAlignment(Pos.TOP_CENTER);
		grid3.setHgap(5);
		grid3.setVgap(10);
		grid3.setPadding(new Insets(25,25,25,25));

		Text balanceText = new Text("Finances");
		balanceText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(balanceText,0,1,2,1);

		Scanner dataScan = null;
		try {
			File dataFile = new File("output/userdata/" + currentUser.getName() + ".txt");
			dataScan = new Scanner(dataFile);
		} catch (IOException e) {
			System.out.println("Failed to load users' data");
			e.printStackTrace();
		}

		double balanceVal = dataScan.nextDouble();
		dataScan.close();
		Label balanceLabel = new Label("Balance: " + balanceVal);
		balanceLabel.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(balanceLabel, 1, 1,2,1);

		Label setBalance = new Label("Set Balance:");
		grid3.add(setBalance,0,2);
		TextField setBalanceT = new TextField();
		grid3.add(setBalanceT,1,2);

		Button b4 = new Button("Set Balance");
		HBox h4 = new HBox(10);
		h4.getChildren().add(b4);
		//h4.setAlignment(Pos.CENTER_LEFT);
		grid3.add(h4, 2, 2);

		b4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Double.parseDouble(setBalanceT.getText())>= 0){
					balanceLabel.setText("Balance: " + Double.parseDouble(setBalanceT.getText()));
					try{
						FileWriter myWriter = new FileWriter("output/userdata/" +currentUser.getName() + ".txt", false);
						myWriter.write(setBalanceT.getText() +"\n");

						Income i = currentUser.getIncomeList().get(0);
						myWriter.write("Income: " + i.getName() + " " + i.getAmount() + " " + i.getFrequencyInDays() + "\n");
						Expense e = currentUser.getExpenseList().get(0); // hardcoded for 1, need loop to get more
						myWriter.write("Expense: " + e.getName() + " " + e.getAmount() + " " + e.getFrequencyInDays() + "\n");
						myWriter.close();
					}catch (IOException e) {
						System.out.println("Failed to write user data");
						e.printStackTrace();
					}

				}
			}
		});

		// income
		Income i = currentUser.getIncomeList().get(0);
		Text incomeText = new Text("Income");
		incomeText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(incomeText,0,4,2,1);

		Label incomeNLabel = new Label("Income Name: " + i.getName());
		grid3.add(incomeNLabel, 0, 5);

		Label incomeALabel = new Label("Income Amount: $" + i.getAmount());
		grid3.add(incomeALabel, 0, 6);

		Label incomeFLabel = new Label("Income Frequency: " + i.getFrequencyInDays() + " days");
		grid3.add(incomeFLabel, 0, 7);

		Button b5 = new Button("Apply Manually");
		HBox h5 = new HBox(10);
		h5.setAlignment(Pos.CENTER_RIGHT);
		h5.getChildren().add(b5);
		grid3.add(h5, 0, 8);

		b5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Scanner dataScan = null;
				try {
					File dataFile = new File("output/userdata/" + currentUser.getName() + ".txt");
					dataScan = new Scanner(dataFile);
				} catch (IOException e) {
					System.out.println("Failed to load users' data");
					e.printStackTrace();
				}

				double balanceVal = dataScan.nextDouble() + i.getAmount();
				dataScan.close();
				String str = String.format("Balance: %.2f", balanceVal);
				balanceLabel.setText(str);

				try{
					FileWriter myWriter = new FileWriter("output/userdata/" + currentUser.getName() + ".txt", false);
					myWriter.write(balanceVal +"\n");

					Income i = currentUser.getIncomeList().get(0);
					myWriter.write("Income: " + i.getName() + " " + i.getAmount() + " " + i.getFrequencyInDays() + "\n");
					Expense e = currentUser.getExpenseList().get(0); // hardcoded for 1, need loop to get more
					myWriter.write("Expense: " + e.getName() + " " + e.getAmount() + " " + e.getFrequencyInDays() + "\n");
					myWriter.close();
				}catch (IOException e) {
					System.out.println("Failed to write user data");
					e.printStackTrace();
				}


			}
		});

		// expense
		Expense e = currentUser.getExpenseList().get(0);
		Text expenseText = new Text("Expense");
		expenseText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(expenseText,1,4,2,1);

		Label expenseNLabel = new Label("Expense Name: " + e.getName());
		grid3.add(expenseNLabel, 1, 5);

		Label expenseALabel = new Label("Expense Amount: $" + e.getAmount());
		grid3.add(expenseALabel, 1, 6);

		Label expenseFLabel = new Label("Expense Frequency: " + e.getFrequencyInDays() + " days");
		grid3.add(expenseFLabel, 1, 7);

		Button b6 = new Button("Apply Manually");
		HBox h6 = new HBox(10);
		h6.setAlignment(Pos.CENTER_RIGHT);
		h6.getChildren().add(b6);
		grid3.add(h6, 1, 8);
		
		// See future balance
		Label futureBalance = new Label("Enter Number of Days:");
		grid3.add(futureBalance,0,60);
		TextField futureBalanceT = new TextField();	
		grid3.add(futureBalanceT,1,60);

		Button futureBalanceButton = new Button("See Future Balance");
		HBox futureHBox = new HBox(10);
		futureHBox.setAlignment(Pos.CENTER_RIGHT);
		futureHBox.getChildren().add(futureBalanceButton);
		grid3.add(futureHBox, 2, 60);

		//Reset Button
		Button resetBalanceButton = new Button("Reset");
		futureHBox.getChildren().add(resetBalanceButton);
		resetBalanceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				balanceLabel.setText("Balance: " + balanceVal);	// Reset balance to actual balance
				futureBalanceT.setText("");				// Reset number of days text box
			}	
		});
		futureBalanceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				double futureTotal = balanceVal;									// Initialize to current balance
				// Inputted number of future days
				int days = Integer.parseInt(futureBalanceT.getText());				
				Double amount;
				int freq = 0;

				// Loop to go through the income list
				for(int i=0; i<currentUser.getIncomeList().size(); i++){
					amount = currentUser.getIncomeList().get(i).getAmount(); 		// Amount to be added to balance from income i
					freq = currentUser.getIncomeList().get(i).getFrequencyInDays(); // Frquency of income i
					int times = days / freq;										// How many times the amount will be added

					if(freq <= days){				
						futureTotal += (times * amount);					
					}
				}
				// Loop to go through the expense list
				for(int i=0; i<currentUser.getExpenseList().size(); i++){
					amount = currentUser.getExpenseList().get(i).getAmount(); 		// Amount to be added to balance from income i
					freq = currentUser.getExpenseList().get(i).getFrequencyInDays();// Frquency of income i
					int times = days / freq;										// How many times the amount will be added

					if(freq <= days){												
						futureTotal -= (amount * times);	
					}
				}
				String futureTotalStr = String.format("%.2f", futureTotal );	// Future total in string format
				if(Integer.parseInt(futureBalanceT.getText())>= 0){		
					balanceLabel.setText("Balance: " + futureTotalStr); 			// Show balance as the future Balance
				}
			}
		});

		b6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Scanner dataScan = null;
				try {
					File dataFile = new File("output/userdata/" +currentUser.getName() + ".txt");
					dataScan = new Scanner(dataFile);
				} catch (IOException e) {
					System.out.println("Failed to load users' data");
					e.printStackTrace();
				}

				double balanceVal = dataScan.nextDouble() - e.getAmount();
				dataScan.close();
				String str = String.format("Balance: %.2f", balanceVal);
				balanceLabel.setText(str);

				try{
					FileWriter myWriter = new FileWriter("output/userdata/" +currentUser.getName() + ".txt", false);
					myWriter.write(balanceVal +"\n");

					Income i = currentUser.getIncomeList().get(0);
					myWriter.write("Income: " + i.getName() + " " + i.getAmount() + " " + i.getFrequencyInDays() + "\n");
					Expense e = currentUser.getExpenseList().get(0); // hardcoded for 1, need loop to get more
					myWriter.write("Expense: " + e.getName() + " " + e.getAmount() + " " + e.getFrequencyInDays() + "\n");
					myWriter.close();
				}catch (IOException e) {
					System.out.println("Failed to write user data");
					e.printStackTrace();
				}


			}
		});


		// scene setup
		Scene account = new Scene(grid3, 250, 500);
		accountStage.setScene(account);
		account.getStylesheets().add(WindowDisplay.class.getResource("styleSheets/style.css").toExternalForm());
		accountStage.show();

	}

	//Creates new user with given name and password
	//return 0 means successful user creation
	//return 1 means no username provided
	//return 2 means no password provided
	//return 3 means username already taken
	private int createNewUser(Users users, String name, String pw)
	{
		if ((users.getUser(name) == null) && (name.length() > 0) && (pw.length() > 0)) {
			User target = users.newUser(name, pw);
			target.addNewBalance(0.00);
			target.addNewIncome(new Income("test", 101.11, 14));
			target.addNewExpense(new Expense("test", 100.50, 7));
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
			if ((users.getList().get(name)).equals(password)) {
				this.currentUser = target;
				currentUser = target;
				//currentUser.addNewIncome(new Income("test", 101.11, 14));
				//currentUser.addNewExpense(new Expense("test", 100.50, 7));
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}
}
