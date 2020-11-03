package app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/isotypeBlue.png")));
            primaryStage.setTitle("Login - Garage Inc.");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Couldn't load some resource");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}