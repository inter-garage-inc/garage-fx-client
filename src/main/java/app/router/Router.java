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

/**
 * The router allows management and switching of the main window of the JavaFX application.
 * It can load and show different scenes, create popup, show and close them.
 * Backing previous scenes is supported by history stacking up option.
 * Data can be sent and claimed between different scenes.
 *
 * Inspired by <a href="https://github.com/Marcotrombino/FXRouter">FXRouter by @Marcotrombino</a>
 *
 * @author jlucasrods
 * @version 1.1
 * @since 2020-11-06
 */
public class Router extends Application {
    private static final String SUF_TITLE = "Garage Inc.";

    private static final String ICON_PATH = "/app/view/images/isotypeBlue.png";

    private static final String FXML_SOURCE = "/app/view/";

    private static final String POPUP_SUB = "popup/";

    private static final Class<?> START_SCENE = LoginController.class;

    private static Stage primaryStage;

    private static Class<?> clazz;

    private static final Stack<StageData> stageDataStack = new Stack<>();;

    private static StageData lastStageData;

    private static Stage lastPopUp;

    private static Object userData;

    @Override
    public void start(Stage primaryStage) {
        Router.primaryStage = primaryStage;
        primaryStage.getIcons().add(loadIcon());
        primaryStage.setResizable(false);

        goTo(START_SCENE);
    }

    /**
     * Helper method which gets fxml path the according to the current controller in {@link #clazz}
     *
     * It is composed by:
     * default fxml dir {@link #FXML_SOURCE} +
     * default popup dir inside fxml dir {@link #POPUP_SUB} only if is a popup +
     * custom fxml path or supposed by controller name
     *
     * @return the fxml path
     */
    private static String getFxmlPath () {
        var annotation = (RouteMapping) clazz.getAnnotation(RouteMapping.class);
        var fxml = (annotation.fxml().equals("")
                ? clazz.getSimpleName().replaceFirst( "Controller$", ".fxml")
                : annotation.fxml());

        return FXML_SOURCE + (annotation.popup() ? POPUP_SUB : "") + fxml;
    }

    /**
     * Helper method which gets window title according to the current controller in {@link #clazz}
     *
     * @return the window title
     */
    private static String getTitle() {
        var annotation = (RouteMapping) clazz.getAnnotation(RouteMapping.class);
        var title = annotation.title().equals("")
                ? ""
                : annotation.title() + "  |  ";
        return title + SUF_TITLE;
    }

    /**
     * helper method which loads application image icon
     *
     * @return the Image icon
     */
    public static Image loadIcon() {
        return new Image(new Object() {}.getClass().getResourceAsStream(ICON_PATH));
    }

    /**
     * Helper method which loads scene from fxml path
     *
     * @return a new Scene from fxml
     */
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

    /**
     *
     * @param clazz controller of scene to switch
     * @param userData Data can be sent and claimed between different scenes.
     * @param stackUpHistory true to stack up history and be able to go back to previous scenes with {@link #back()} or
     *                       false to clear stack and not stack up on switching or
     *                       null to switching without cleaning stack or stack up the current scene
     */
    public static void goTo(Class<?> clazz, Object userData, Boolean stackUpHistory) {
        setUserData(userData);
        goTo(clazz, stackUpHistory);
    }

    public static void goTo(Class<?> clazz, Object userData) {
        setUserData(userData);
        goTo(clazz, false);
    }

    public static void goTo(Class<?> clazz) {
        goTo(clazz, false);
    }

    /**
     * Switch to a new scene corresponding to controller
     *
     * @param clazz controller of scene to switch
     * @param stackUpHistory true to stack up history and be able to go back to previous scenes with {@link #back()} or
     *                       false to clear stack and not stack up on switching or
     *                       null to switching without cleaning stack or stack up the current scene
     */
    public static void goTo(Class<?> clazz, Boolean stackUpHistory) {
        Router.clazz = clazz;

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

    /**
     * Go back to the previous scene
     */
    public static void back() {
        try {
            lastStageData = stageDataStack.pop();
            primaryStage.setTitle(lastStageData.getTitle());
            primaryStage.setScene(lastStageData.getScene());
        } catch (IndexOutOfBoundsException exception) {
            throw exception;
        }
    }

    public static Boolean hasHistory() {
        return !stageDataStack.isEmpty();
    }

    /**
     * This method the used to {@link #showPopUp(Class)} the pop up
     too is passed the {@link Object} using {@link #setUserData(Object) and {@link #getUserData()}}
     And a {@link Integer} to close the pop up, closing using {@link #closePopUp(Integer)}
     *
     * @param clazz controller of scene to switch
     * @param userData Data can be sent and claimed between different scenes.
     * @param time time to close the pop up
     */
    public static void showPopUp(Class<?> clazz, Object userData, Integer time) {
        setUserData(userData);
        showPopUp(clazz);
        closePopUp(time);
    }

    /**
     * This method use {@link #showPopUp(Class)} to show the pop up
     too is passed the {@link Object} using {@link #setUserData(Object) and {@link #getUserData()}}
     *
     * @param clazz controller of scene to switch
     * @param userData Data can be sent and claimed between different scenes.
     */
    public static void showPopUp(Class<?> clazz, Object userData) {
        setUserData(userData);
        showPopUp(clazz);
    }

    /**
     * This method use {@link #showPopUp(Class)} to show the pop up
     and with time to closing using {@link #closePopUp(Integer)}
     * @param clazz controller of scene to switch
     * @param time time to close the pop up
     */
    public static void showPopUp(Class<?> clazz, Integer time) {
        showPopUp(clazz);
        closePopUp(time);
    }

    /**
     * This method show the pop up
     * @param clazz controller of scene to switch
     */
    public static void showPopUp(Class<?> clazz) {
        Router.clazz = clazz;
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

    /**
     * This method close the pop up using {@link #closePopUp(Integer)}
     */
    public static void closePopUp() {
        closePopUp(0);
    }

    /**
     * This method close the pop up with {@link #closePopUp(Integer)}
     * @param time time to close the pop-up
     */
    public static void closePopUp(Integer time) {
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished(event -> lastPopUp.close());
        delay.play();
    }

    /**
     * This method is used to make the system more visible to the user
     */
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
