/*
 * Author(s): Danyel Munson, Evan Bradford, Calder Neely, Noah Waldorf, Samuel Wright, Pantaree Prathongkham
 * Created: 10-11-2023
 * Last Updated: 10-26-2023
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WindowDisplay extends Application {

	GridPane grid3;

	public static void main(String[] args) {
		launch(args);
	}

	private Screen screen = null;
	private	UserManager userList = null;
	private User currentUser = null;

	// Font Sizes
	int bigText = 60;
	int smallText = 20;

	String current_style = "resources/styleSheets/styleGreen.css";
	Scene scene = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		userList = new UserManager();

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

		// Style Changing Buttons
		Label styleLabel = new Label("Themes");
		grid.add(styleLabel, 1, 29);

		Button greenB = new Button("Forest Green");
		HBox greenH = new HBox(5);
		greenH.getChildren().add(greenB);
		grid.add(greenH, 1, 30);

		Button darkB = new Button("Stone Gray");
		HBox darkH = new HBox(5);
		darkH.getChildren().add(darkB);
		grid.add(darkH, 1, 31);

		Button blueB = new Button("Lake Blue");
		HBox blueH = new HBox(5);
		blueH.getChildren().add(blueB);
		grid.add(blueH, 2, 30);

		Button redB = new Button("Clay Red");
		HBox redH = new HBox(5);
		redH.getChildren().add(redB);
		grid.add(redH, 2, 31);

		greenB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				current_style = "resources/styleSheets/styleGreen.css";
				scene.getStylesheets().clear();
				scene.getStylesheets().add(WindowDisplay.class.getResource(current_style).toExternalForm());
			}
		});

		darkB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				current_style = "resources/styleSheets/styleDark.css";
				scene.getStylesheets().clear();
				scene.getStylesheets().add(WindowDisplay.class.getResource(current_style).toExternalForm());
			}
		});

		blueB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				current_style = "resources/styleSheets/styleBlue.css";
				scene.getStylesheets().clear();
				scene.getStylesheets().add(WindowDisplay.class.getResource(current_style).toExternalForm());
			}
		});

		redB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				current_style = "resources/styleSheets/styleRed.css";
				scene.getStylesheets().clear();
				scene.getStylesheets().add(WindowDisplay.class.getResource(current_style).toExternalForm());
			}
		});


		// User Creator Labels and Textfields
		Text userCreation = new Text("  Create User");
		userCreation.setFont(Font.font("Cambria", FontWeight.BOLD, bigText));
		grid.add(userCreation, 0, 1, 2, 1);

		Label userName = new Label("New Username:");
		grid.add(userName, 0, 2);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 2);

		Label email = new Label("                    Email:");
		grid.add(email, 0, 3);
		TextField emailField = new TextField();
		grid.add(emailField, 1, 3);

		Label pw = new Label("New Password: ");
		grid.add(pw, 0, 4);
		PasswordField pwBox = new PasswordField();
		pwBox.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(pwBox, 1, 4);

		// User Creator Button
		Button b1 = new Button("Create New User");
		HBox h1 = new HBox(10);
		h1.setAlignment(Pos.CENTER_RIGHT);
		h1.getChildren().add(b1);
		grid.add(h1, 1, 5);

		Text createError = new Text();
		createError.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(createError, 1,6);
		createError.setFill(Color.DARKRED);

		// Button Code
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int createUserResult = createNewUser(userList, userTextField.getText(), emailField.getText(),  pwBox.getText());
				if (createUserResult == 0) {
					createError.setText("New user " + userTextField.getText() + " created!");
					createError.setFill(Color.DARKBLUE);
					userTextField.clear();
					emailField.clear();
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
				} else if (createUserResult == 4) {
					createError.setText("Error: Email Needed");
					createError.setFill(Color.DARKRED);
				}
			}
		});

		// Login Labels and Textfields
		Text loginText = new Text("        Login");
		loginText.setFont(Font.font("Cambria", FontWeight.BOLD, bigText));
		grid.add(loginText, 0, 7, 2, 1);

		Label userName2 = new Label("        Username:");
		grid.add(userName2, 0, 8);
		TextField userTextField2 = new TextField();
		grid.add(userTextField2, 1, 8);

		Label pw2 = new Label("         Password:");
		grid.add(pw2, 0, 9);
		PasswordField pwBox2 = new PasswordField();
		pwBox2.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(pwBox2, 1, 9);

		// Login Button
		Button b2 = new Button("Login");
		HBox h2 = new HBox(10);
		h2.setAlignment(Pos.CENTER_RIGHT);
		h2.getChildren().add(b2);
		grid.add(h2, 1, 10);

		Text loginError = new Text();
		loginError.setFont(Font.font("Cambria", FontWeight.NORMAL, smallText));
		grid.add(loginError, 1,11);

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
					loadAccountDetails(currentUser, current_style);
					((Node) (event.getSource())).getScene().getWindow().hide();
				}
			}
		});



		// Commented out while working on ui
		// Group root = new Group(grid);

		scene = new Scene(grid, 250, 500);
		// Add your content to the scene

		//Set the scene on the stage and show the stage
		primaryStage.setScene(scene);
		scene.getStylesheets().add(WindowDisplay.class.getResource(current_style).toExternalForm());
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		userList.save();
	}

	//Method that loads the account details tab
	private void loadAccountDetails(User currentUser, String current) {
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

		Label password = new Label("Password: " +currentUser.getPassword());
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
		grid3 = new GridPane();
		grid3.setAlignment(Pos.TOP_LEFT);
		grid3.setHgap(5);
		grid3.setVgap(10);
		grid3.setPadding(new Insets(25,25,25,25));

		Text balanceText = new Text("Finances");
		balanceText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(balanceText,0,1,2,1);

		double balanceVal = currentUser.getBalance();
		Label balanceLabel = new Label("Balance: " + balanceVal);
		balanceLabel.getStyleClass().add("balanceDisplay");
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
				if(Double.parseDouble(setBalanceT.getText())>= 0) {
					balanceLabel.setText("Balance: " + Double.parseDouble(setBalanceT.getText()));
					currentUser.setBalance(Double.parseDouble(setBalanceT.getText()));
				}
			}
		});

		// START SAM'S WORK ON CUSTOM INCOME AND EXPENSE BUTTONS

		// ADD INCOME BUTTON DEFINITION
		Button addIncomeButton = new Button("Add Income");
		HBox addIncomeHBox = new HBox(10);
		addIncomeHBox.setAlignment(Pos.CENTER_RIGHT);
		addIncomeHBox.getChildren().add(addIncomeButton);
		grid3.add(addIncomeHBox, 2, 6);

		// INCOME INPUT FIELDS (IN DIALOG)
		TextField incomeNameField = new TextField();
		TextField incomeAmountField = new TextField();
		TextField incomeFrequencyField = new TextField();

		// DIALOG FOR ADDING CUSTOM INCOME
		DialogPane dialogPane = new DialogPane();
		dialogPane.setHeaderText("Enter Income Details");
		dialogPane.setContent(new VBox(10,
				new Label("Income Name:"), incomeNameField,
				new Label("Income Amount:"), incomeAmountField,
				new Label("Income Frequency (in days):"), incomeFrequencyField
		));

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setDialogPane(dialogPane);
		dialog.setTitle("Add Income");

		// OK BUTTON DEFINITION
		ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

		addIncomeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Show the dialog and wait for user input
				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == addButton) {
						String name = incomeNameField.getText();
						double amount = Double.parseDouble(incomeAmountField.getText());
						int frequencyInDays = Integer.parseInt(incomeFrequencyField.getText());
						// Create a new Income object with the entered details
						Income newIncome = new Income(name, amount, frequencyInDays);
						// You can add this newIncome to your user's income list here
						currentUser.addIncome(newIncome);
						dialog.close();
					}
					return null;
				});
				dialog.showAndWait();
				updateIncomeDisplay();
			}
		});

		// Create a button to clear all incomes
		Button clearIncomesButton = new Button("Clear Incomes");
		HBox clearIncomesHBox = new HBox(10);
		clearIncomesHBox.setAlignment(Pos.CENTER_RIGHT);
		clearIncomesHBox.getChildren().add(clearIncomesButton);
		grid3.add(clearIncomesHBox, 2, 7);

		clearIncomesButton.setOnAction(event -> {
			// Clear all incomes in the current user's income list
			currentUser.getIncomeList().clear();
			clearIncomeDisplay();
		});

		// add custom expense button
		Button addExpenseButton = new Button("Add Expense");
		HBox addExpenseHBox = new HBox(10);
		addExpenseHBox.setAlignment(Pos.CENTER_RIGHT);
		addExpenseHBox.getChildren().add(addExpenseButton);
		// add button to grid 3
		grid3.add(addExpenseHBox, 3, 6);

		// EXPENSE INPUT FIELDS (IN DIALOG)
		TextField expenseNameField = new TextField();
		TextField expenseAmountField = new TextField();
		TextField expenseFrequencyField = new TextField();

		DialogPane expenseDialogPane = new DialogPane();
		expenseDialogPane.setHeaderText("Enter Expense Details");
		expenseDialogPane.setContent(new VBox(10,
				new Label("Expense Name:"), expenseNameField,
				new Label("Expense Amount:"), expenseAmountField,
				new Label("Expense Frequency (in days):"), expenseFrequencyField
		));

		Dialog<ButtonType> expenseDialog = new Dialog<>();
		expenseDialog.setDialogPane(expenseDialogPane);
		expenseDialog.setTitle("Add Expense");

		ButtonType addExpenseOKButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		expenseDialog.getDialogPane().getButtonTypes().addAll(addExpenseOKButton, ButtonType.CANCEL);

		addExpenseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				expenseDialog.setResultConverter(dialogButton -> {
					if (dialogButton == addExpenseOKButton) {
						String name = expenseNameField.getText();
						double amount = Double.parseDouble(expenseAmountField.getText());
						int frequencyInDays = Integer.parseInt(expenseFrequencyField.getText());
						// Create a new Expense object with the entered details
						Expense newExpense = new Expense(name, amount, frequencyInDays);
						// You can add this newExpense to your user's expense list here
						currentUser.addExpense(newExpense);
						expenseDialog.close();
					}
					return null;
				});
				expenseDialog.showAndWait();
				updateExpenseDisplay();
			}
		});

		// Create a button to clear all incomes
		Button clearExpensesButton = new Button("Clear Expenses");
		HBox clearExpensesHBox = new HBox(10);
		clearExpensesHBox.setAlignment(Pos.CENTER_RIGHT);
		clearExpensesHBox.getChildren().add(clearExpensesButton);
		grid3.add(clearExpensesHBox, 3, 7);

		clearExpensesButton.setOnAction(event -> {
			// Clear all expenses in the current user's expense list
			currentUser.getExpenseList().clear();
			clearExpenseDisplay();
		});

		// END SAM'S WORK ON CUSTOM INCOME AND EXPENSE BUTTONS

		// Income Startup Column
		Text incomeText = new Text("Income");
		incomeText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(incomeText, 0, 4, 2, 1);

		List<Income> incomeList = currentUser.getIncomeList();
		int incomeRow = 5; // Starting row for displaying income items

		for (Income i : incomeList) { // Loop through each income item
			Label incomeNLabel = new Label("Income Name: " + i.getName());
			grid3.add(incomeNLabel, 0, incomeRow++);

			Label incomeALabel = new Label("Income Amount: $" + i.getAmount());
			grid3.add(incomeALabel, 0, incomeRow++);

			Label incomeFLabel = new Label("Income Frequency: " + i.getFrequencyInDays() + " days");
			grid3.add(incomeFLabel, 0, incomeRow++);
		}

		Button b5 = new Button("Apply Manually");
		HBox h5 = new HBox(10);
		h5.setAlignment(Pos.CENTER_RIGHT);
		h5.getChildren().add(b5);
		grid3.add(h5, 2, 5);

		b5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				double balanceVal = currentUser.getBalance();
				List<Income> incomeList = currentUser.getIncomeList();
				if (!incomeList.isEmpty()) {
					Income i = incomeList.get(0);
					balanceVal += i.getAmount();
				}
				String str = String.format("Balance: %.2f", balanceVal);
				currentUser.setBalance(balanceVal);
				balanceLabel.setText(str);
			}
		});

		// Expense Startup Column
		Text expenseText = new Text("Expense");
		expenseText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
		grid3.add(expenseText, 1, 4, 2, 1);

		List<Expense> expenseList = currentUser.getExpenseList();
		int expenseRow = 5; // Starting row for displaying expense items

		for (Expense e : expenseList) { // Loop through each expense item
			Label expenseNLabel = new Label("Expense Name: " + e.getName());
			grid3.add(expenseNLabel, 1, expenseRow++);

			Label expenseALabel = new Label("Expense Amount: $" + e.getAmount());
			grid3.add(expenseALabel, 1, expenseRow++);

			Label expenseFLabel = new Label("Expense Frequency: " + e.getFrequencyInDays() + " days");
			grid3.add(expenseFLabel, 1, expenseRow++);
		}

		Button b6 = new Button("Apply Manually");
		HBox h6 = new HBox(10);
		h6.setAlignment(Pos.CENTER_RIGHT);
		h6.getChildren().add(b6);
		grid3.add(h6, 3, 5);

		b6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				double balanceVal = currentUser.getBalance();
				List<Expense> expenseList = currentUser.getExpenseList();
				if (!expenseList.isEmpty()) {
					Expense e = expenseList.get(0);
					balanceVal -= e.getAmount();
				}
				String str = String.format("Balance: %.2f", balanceVal);
				currentUser.setBalance(balanceVal);
				balanceLabel.setText(str);
			}
		});

		// See future balance
		Label futureBalance = new Label("Enter Number of Days:");
		grid3.add(futureBalance,0,49);
		TextField futureBalanceT = new TextField();
		grid3.add(futureBalanceT,1,49);

		Button futureBalanceButton = new Button("See Future Balance");
		HBox futureHBox = new HBox(10);
		futureHBox.setAlignment(Pos.CENTER_RIGHT);
		futureHBox.getChildren().add(futureBalanceButton);
		grid3.add(futureHBox, 2, 49);

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
				double currentBalance = currentUser.getBalance();
				double futureTotal = currentBalance;								// Initialize to current balance
				// Inputted number of future days
				int days = Integer.parseInt(futureBalanceT.getText());
				Double amount;
				int freq = 0;

				ObservableList<PieChart.Data> expensePieChartData = FXCollections.observableArrayList();
				ObservableList<PieChart.Data> incomePieChartData = FXCollections.observableArrayList();

				// Loop to go through the income list
				for(int i=0; i<currentUser.getIncomeList().size(); i++){
					amount = currentUser.getIncomeList().get(i).getAmount(); 		// Amount to be added to balance from income i
					freq = currentUser.getIncomeList().get(i).getFrequencyInDays(); // Frquency of income i
					int times = days / freq;										// How many times the amount will be added

					if(freq <= days){
						futureTotal += (times * amount);
					}

					double dailyAmount = amount/freq;
					incomePieChartData.add(new PieChart.Data(currentUser.getIncomeList().get(i).getName(), dailyAmount));
				}
				// Loop to go through the expense list
				for(int i=0; i<currentUser.getExpenseList().size(); i++){
					amount = currentUser.getExpenseList().get(i).getAmount(); 		// Amount to be added to balance from income i
					freq = currentUser.getExpenseList().get(i).getFrequencyInDays();// Frquency of income i
					int times = days / freq;										// How many times the amount will be added

					if(freq <= days){
						futureTotal -= (amount * times);
					}
					double dailyAmount = amount/freq;
					expensePieChartData.add(new PieChart.Data(currentUser.getExpenseList().get(i).getName(), dailyAmount));
				}
				String futureTotalStr = String.format("%.2f", futureTotal );	// Future total in string format
				if(Integer.parseInt(futureBalanceT.getText())>= 0){
					balanceLabel.setText("Balance: " + futureTotalStr); 			// Show balance as the future Balance
				}

				//Show graphs
				NumberAxis yAxis;
				if (currentBalance <= futureTotal) {
					yAxis = new NumberAxis(0, futureTotal * 1.1, futureTotal / 25);
				} else {
					yAxis = new NumberAxis(futureTotal, currentBalance, futureTotal / 25);
				}
				NumberAxis xAxis = new NumberAxis(0, days, days/15);

				LineChart lineChart = new LineChart(xAxis, yAxis);

				XYChart.Series series = new XYChart.Series();
				series.setName("Projected future balance");

				series.getData().add(new XYChart.Data(0, currentBalance));
				series.getData().add(new XYChart.Data(days-1, futureTotal));

				double projectedBalance = currentBalance;
				boolean didBalanceUpdate;
				for (int i = 0; i < days; i++) {
					didBalanceUpdate = false;
					for (Expense e: currentUser.getExpenseList()) {
						if ((i % e.getFrequencyInDays()) == 0) {
							projectedBalance -= e.getAmount();
							didBalanceUpdate = true;
						}
					}
					for (Income x: currentUser.getIncomeList()) {
						if ((i % x.getFrequencyInDays()) == 0)  {
							projectedBalance += x.getAmount();
							didBalanceUpdate = true;
						}
					}

					if (didBalanceUpdate) {
						series.getData().add(new XYChart.Data(i, projectedBalance));
					}
				}

				lineChart.getData().add(series);
				grid3.add(lineChart, 0, 20, 2, 20);

				PieChart expensePieChart = new PieChart(expensePieChartData);
				expensePieChart.setTitle("Expenses breakdown");
				PieChart incomePieChart = new PieChart(incomePieChartData);
				incomePieChart.setTitle("Income breakdown");
				grid3.add(expensePieChart, 2, 20, 7, 7);
				grid3.add(incomePieChart, 2, 30, 10, 10);
			}
		});

		//Start Pan's work (Sprint 3) / Danyel (Sprint 4)
		//Setting Button on account screen
		Button settingsButton = new Button("User Settings");
		HBox settingsBox = new HBox(20);
		settingsBox.setAlignment(Pos.BOTTOM_RIGHT);
		settingsBox.getChildren().add(settingsButton);
		grid3.add(settingsBox, 50, 50);

		// Settings Grid
		GridPane settingsGrid = new GridPane();
		settingsGrid.setAlignment(Pos.CENTER);
		settingsGrid.setHgap(5);
		settingsGrid.setVgap(10);
		settingsGrid.setPadding(new Insets(50, 25, 25, 25));

		Stage settingsStage = new Stage();
		Scene settingsScene = new Scene(settingsGrid,250,500);
		Scene account = new Scene(grid3, 250, 500);

		settingsButton.setOnAction(event -> {
				settingsStage.setTitle("User Settings");
				settingsStage.setX(screen.getVisualBounds().getMinX());
				settingsStage.setY(screen.getVisualBounds().getMinY());
				settingsStage.setWidth(screen.getVisualBounds().getWidth());
				settingsStage.setHeight(screen.getVisualBounds().getHeight());

				// Display current user info
				Text currentSettingsText = new Text("Current User Settings");
				currentSettingsText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
				settingsGrid.add(currentSettingsText, 0,0);

				Label currentUsernameLabel = new Label("Username: " + currentUser.getName());
				Label currentPasswordLabel = new Label("Password: " + currentUser.getPassword());
				Label currentEmailLabel = new Label("Email: " + currentUser.getEmail());

				settingsGrid.add(currentUsernameLabel,0,1);
				settingsGrid.add(currentPasswordLabel,0,2);
				settingsGrid.add(currentEmailLabel,0,3);

				Text userSettingsText = new Text("Change User Settings");
				userSettingsText.setFont(Font.font("TimesRoman", FontWeight.BOLD, 30));
				settingsGrid.add(userSettingsText, 0, 4,2,1);

				// Create and configure the username, password, email labels, textfields, and buttons
				Label usernameLabel = new Label("New Username");
				Label passwordLabel = new Label("New password");
				Label emailLabel = new Label("New Email");

				TextField newUsername = new TextField();
				PasswordField newPassword = new PasswordField();
				TextField newEmail = new TextField();

				Button changeUsernameButton = new Button("Change Username");
				Button changePasswordButton = new Button("Change Password");
				Button changeEmailButton = new Button("Change Email");
				// Add everything to the grid
				settingsGrid.add(usernameLabel,0,5);
				settingsGrid.add(passwordLabel,0,6);
				settingsGrid.add(emailLabel,0,7);

				settingsGrid.add(newUsername, 1, 5);
				settingsGrid.add(newPassword, 1, 6);
				settingsGrid.add(newEmail, 1, 7);

				settingsGrid.add(changeUsernameButton, 2,5);
				settingsGrid.add(changePasswordButton, 2,6);
				settingsGrid.add(changeEmailButton, 2,7);

				// Back button
				Button settingsBackButton = new Button("Back");
				settingsBackButton.setFont(Font.font("TimesRoman", FontWeight.BOLD, 20));
				settingsGrid.add(settingsBackButton, 0,9);
				settingsBackButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						settingsStage.setScene(account);		// Go back to the account screen
					}
				});				
				// Methods to change the value of username, password, or email when corresponding button is pressed
				changeUsernameButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(newUsername.getText() != null){
							String updatedUsername = newUsername.getText();
							currentUser.setName(updatedUsername);
							newUsername.setText("");			// Reset text box
							currentUsernameLabel.setText("Username: " + currentUser.getName());	// Update the on screen current info
						}
					}
				});
				changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(newPassword.getText() != null){
							String updatedPassword = newPassword.getText();
							currentUser.setName(updatedPassword);
							newPassword.setText("");			// Reset text box
							currentPasswordLabel.setText("Password: " + currentUser.getPassword());	// Update the on screen current info
						}
					}
				});
				changeEmailButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(newEmail.getText() != null){
							String updatedEmail = newEmail.getText();
							currentUser.setName(updatedEmail);
							newEmail.setText("");			// Reset text box
							currentEmailLabel.setText("Email: " + currentUser.getEmail());	// Update the on screen current info
						}
					}
				});
				settingsStage.setScene(settingsScene);
				settingsStage.showAndWait();
			}
		);

		//End of Pan's work(Sprint 3) / Danyel (Sprint 4)

		// scene setup
		accountStage.setScene(account);
		account.getStylesheets().add(WindowDisplay.class.getResource(current).toExternalForm());
		accountStage.show();


	}

	//Creates new user with given name and password
	//return 0 means successful user creation
	//return 1 means no username provided
	//return 2 means no password provided
	//return 3 means username already taken
	//return 4 means no email provided
	private int createNewUser(UserManager users, String name, String email, String pw)
	{
		if ((users.getUser(name) == null) && (name.length() > 0) && (pw.length() > 0) && (email.length() > 0)) {
			User target = users.newUser(name, email, pw, null);
			target.addNewBalance(0.00);
			return 0;
		} else if (name.length() == 0) {
			return 1;
		} else if(email.length() == 0) {
			return 4;
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
	private int attemptLogin(UserManager users, User currentUser, String name, String password)
	{
		//find
		User target = users.getUser(name);
		if (target != null) {
			if (target.getPassword().equals(password)) {
				this.currentUser = target;
				currentUser = target;
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	private void updateIncomeDisplay() {
		// Assuming grid3 is the GridPane where you display the income information
		// Clear existing income display elements from the grid3
		clearIncomeDisplay();

		// Get updated income list
		List<Income> incomeList = currentUser.getIncomeList();
		int incomeRow = 5; // Starting row for displaying income, adjust as necessary

		for (Income income : incomeList) {
			// Create and add new labels for each income item
			grid3.add(new Label("Income Name: " + income.getName()), 0, incomeRow++);
			grid3.add(new Label("Income Amount: $" + income.getAmount()), 0, incomeRow++);
			grid3.add(new Label("Income Frequency: " + income.getFrequencyInDays() + " days"), 0, incomeRow++);
		}
	}

	private void updateExpenseDisplay() {
		// Assuming grid3 is the GridPane where you display the expense information
		// Clear existing expense display elements from the grid3
		clearExpenseDisplay();

		// Get updated expense list
		List<Expense> expenseList = currentUser.getExpenseList();
		int expenseRow = 5; // Starting row for displaying expense, adjust as necessary

		for (Expense expense : expenseList) {
			// Create and add new labels for each expense item
			grid3.add(new Label("Expense Name: " + expense.getName()), 1, expenseRow++);
			grid3.add(new Label("Expense Amount: $" + expense.getAmount()), 1, expenseRow++);
			grid3.add(new Label("Expense Frequency: " + expense.getFrequencyInDays() + " days"), 1, expenseRow++);
		}
	}

	private void clearIncomeDisplay() {
		// Assuming income information is in column 0
		int incomeColumn = 0;
		// Starting from row 5 to row 8 for income display based on the provided layout
		for (int row = 5; row < 14; row++) {
			removeNodesFromGridPaneByRowAndColumn(grid3, incomeColumn, row);
		}
	}

	private void clearExpenseDisplay() {
		// Assuming expense information is in column 1
		int expenseColumn = 1;
		// Starting from row 5 to row 8 for expense display based on the provided layout
		for (int row = 5; row < 14; row++) {
			removeNodesFromGridPaneByRowAndColumn(grid3, expenseColumn, row);
		}
	}

	// Utility method to remove nodes by row and column in a GridPane
	private void removeNodesFromGridPaneByRowAndColumn(GridPane gridPane, int column, int row) {
		Set<Node> nodesToRemove = new HashSet<>();
		for (Node node : gridPane.getChildren()) {
			// Check the row and column index of the node (null check is necessary)
			Integer rowIndex = GridPane.getRowIndex(node);
			Integer colIndex = GridPane.getColumnIndex(node);
			rowIndex = rowIndex == null ? 0 : rowIndex; // Default to 0 if no index is set
			colIndex = colIndex == null ? 0 : colIndex; // Default to 0 if no index is set
			if (rowIndex == row && colIndex == column) {
				nodesToRemove.add(node);
			}
		}
		// Remove the collected nodes
		gridPane.getChildren().removeAll(nodesToRemove);
	}
}
