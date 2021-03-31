package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the program that contains the main method.
 */
public class Main extends Application {
    public double width = 600;
    public double height = 550;

    /**
     * Overridden method of the Application class that initializes and shows the stage.
     * @param primaryStage stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Molecule Constructor");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Starting method of the program.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
