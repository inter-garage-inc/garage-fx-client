package app.router;

import app.controller.LoginController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Stack;


public class Router extends Application {
    private static final String SUF_TITLE = "Garage Inc.";

    private static final String ICON_PATH = "/app/view/images/isotypeBlue.png";

    private static final String FXML_SOURCE = "/app/view/";

    private static final String POPUP_SUB = "popup/";

    private static Stage primaryStage;

    private static Class<?> c;

    private static final Stack<StageData> stageDataStack = new Stack<>();;

    private static StageData lastStageData;

    private static Stage lastPopUp;

    private static Object userData;

    @Override
    public void start(Stage primaryStage) {
        Router.primaryStage = primaryStage;
        primaryStage.getIcons().add(loadIcon());
        primaryStage.setResizable(false);

        goTo(LoginController.class);
    }

    private static String getFxmlPath () {
        var annotation = (RouteMapping) c.getAnnotation(RouteMapping.class);
        var fxml = (annotation.fxml().equals("") ?
                c.getSimpleName().replaceFirst( "Controller$", "") + ".fxml" :
                annotation.fxml());

        return FXML_SOURCE + (annotation.popup() ? POPUP_SUB : "") + fxml;
    }

    private static String getTitle() {
        var annotation = (RouteMapping) c.getAnnotation(RouteMapping.class);
        return (annotation.title().equals("") ?
                "" :
                annotation.title() + "  |  ")
                + SUF_TITLE;
    }

    public static Image loadIcon() {
        return new Image(new Object() {}.getClass().getResourceAsStream(ICON_PATH));
    }

    public static Scene loadScene() {
        try {
            Parent parent = FXMLLoader.load(new Object() {}.getClass().getResource(getFxmlPath()));
            return new Scene(parent);
        } catch (IOException exception) {
            System.err.println("Unable to load fxml");
            exception.printStackTrace();
        }
        return null;
    }

    public static void goTo(Class<?> c, Object userData, Boolean stackUpHistory) {
        setUserData(userData);
        goTo(c, stackUpHistory);
    }

    public static void goTo(Class<?> c, Object userData) {
        setUserData(userData);
        goTo(c, false);
    }

    public static void goTo(Class<?> c) {
        goTo(c, false);
    }

    public static void goTo(Class<?> c, Boolean stackUpHistory) {
        Router.c = c;

        if(stackUpHistory != null) {
            if(stackUpHistory) {
                stageDataStack.push(lastStageData);
            } else {
                stageDataStack.clear();
                if (lastStageData != null) {
                    lastStageData.setScene(null); // Allows garbage to do its job
                }
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

    public static Boolean hasHistory() {
        return !stageDataStack.isEmpty();
    }

    public static void showPopUp(Class<?> clazz, Object userData, Integer time) {
        showPopUp(clazz);
        setUserData(userData);
        closePopUp(time);
    }

    public static void showPopUp(Class<?> clazz, Object userData) {
        showPopUp(clazz);
        setUserData(userData);
    }

    public static void showPopUp(Class<?> clazz, Integer time) {
        showPopUp(clazz);
        closePopUp(time);
    }

    public static void showPopUp(Class<?> c) {
        Router.c = c;
        if(lastPopUp != null) {
            closePopUp();
        }
        lastPopUp = new Stage();
        lastPopUp.setScene(loadScene());
        lastPopUp.setTitle(getTitle());
        lastPopUp.setResizable(false);
        lastPopUp.initOwner(primaryStage);
        lastPopUp.initModality(Modality.WINDOW_MODAL);
        lastPopUp.show();
    }

    public static void closePopUp() {
        closePopUp(0);
    }

    public static void closePopUp(Integer time) {
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished(event -> lastPopUp.close());
        delay.play();
    }

    public static void reOpenEffect() {
        primaryStage.hide();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void setUserData(Object userData) {
        Router.userData = userData;
    }

    public static Object getUserData() {
        return userData;
    }

}
