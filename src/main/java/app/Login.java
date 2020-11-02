package app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm()); already importing in fxml
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/isotypeBlue.png")));
            primaryStage.setTitle("Login - Garage Inc.");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Impossível carregar fxml");
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonz() {
        System.out.println("Hello");
    }
}