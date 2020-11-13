package app.router;

import app.controller.LoginController;
import app.controller.RegisterCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Stack;


public class Router extends Application {
    private static final String sufTitle = "Garage Inc.";

    private static final String iconPath = "/app/view/images/isotypeBlue.png";

    private static final String fxmlSource = "/app/view/";

    private static Stage primaryStage;

    private static Class<?> c;

    private static final Stack<StageData> stageDataStack = new Stack<>();;

    private static StageData lastStageData;

    @Override
    public void start(Stage primaryStage) {
        Router.primaryStage = primaryStage;
        primaryStage.getIcons().add(loadIcon());
        primaryStage.setResizable(false);

        goTo(LoginController.class);
    }

    private static String getFxmlPath () {
        var annotation = (RouteMapping) c.getAnnotation(RouteMapping.class);
        return fxmlSource + (annotation.fxml().equals("") ?
                c.getSimpleName().replaceFirst( "Controller$", "") + ".fxml" :
                annotation.fxml());
    }

    private static String getTitle() {
        var annotation = (RouteMapping) c.getAnnotation(RouteMapping.class);
        return (annotation.title().equals("") ?
                "" :
                annotation.title() + "  |  ")
                + sufTitle;
    }

    public static Image loadIcon() {
        return new Image(new Object() {}.getClass().getResourceAsStream(iconPath));
    }

    public static Scene loadScene() {
        try {
            Parent parent = FXMLLoader.load(new Object() {}.getClass().getResource(getFxmlPath()));
            return new Scene(parent);
        } catch (IOException exception) {
            System.err.println("Unable to load fxml");
        }
        return null;
    }

    public static void goTo(Class<?> c) {
        goTo(c, false);
    }

    public static void goTo(Class<?> c, Boolean stackUpHistory) {
        Router.c = c;

        if(stackUpHistory) {
            stageDataStack.push(lastStageData);
        } else {
            stageDataStack.clear();
            if(lastStageData != null) {
                lastStageData.setScene(null); // Allows garbage to do its job
            }
        }

        var title = getTitle();
        primaryStage.setTitle(title);

        var scene = loadScene();
        primaryStage.setScene(scene);

        lastStageData = StageData
                .builder()
                .title(title)
                .scene(scene)
                .build();

        primaryStage.show();
    }

    public static void back() {
        try {
            lastStageData = stageDataStack.pop();
            primaryStage.setTitle(lastStageData.getTitle());
            primaryStage.setScene(lastStageData.getScene());
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("There's no stage data stacked up to get back.");
            throw exception;
        }
    }

    public static void showPopUp(Class<?> c) {
        Router.c = c;
        var stage = new Stage();
        stage.setScene(loadScene());
        stage.setTitle(getTitle());
        stage.setResizable(false);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public static void reOpenEffect() {
        primaryStage.hide();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
