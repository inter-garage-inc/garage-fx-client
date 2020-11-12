package app;

import app.router.Router;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        Application.launch(Router.class, args);
    }
}

