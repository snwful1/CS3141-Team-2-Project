import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowDisplay extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Financial Assistant");
		
		//Get the primary screen
		Screen screen = Screen.getPrimary();
		
		// Set the stage dimensions to match the screen dimensions
        primaryStage.setX(screen.getVisualBounds().getMinX());
        primaryStage.setY(screen.getVisualBounds().getMinY());
        primaryStage.setWidth(screen.getVisualBounds().getWidth());
        primaryStage.setHeight(screen.getVisualBounds().getHeight());
		
		// Create your JavaFX content (e.g., a scene with a layout)
        Group root = new Group();
        Scene scene = new Scene(root);

        // Add your content to the scene

		//Set the scene on the stage and show the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
