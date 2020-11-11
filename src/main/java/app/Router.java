package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Router extends Application {
    private static Stage primaryStage;
    private static String sufTitle = " | Garage Inc.";
    private static String fxmlSource = "/app/view/";
    private static HashMap<String, SceneRoute> sceneRouteMap;
    private static final Stack<SceneRoute> sceneStack = new Stack<>();
    private static SceneRoute currentSceneRoute;

    @Override
    public void start(Stage primaryStage) {
        Router.primaryStage = primaryStage;

        Image image = new Image(getClass().getResourceAsStream("view/images/isotypeBlue.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setResizable(false);

        performMapping();

        printSceneRouteMap(); // for dev debugging only

        Router.goTo("login");
    }

    public static void performMapping() {
        var file = new File(new Object() {}.getClass().getResource("/sceneRouteMap.json").getFile());
        var mapper = new ObjectMapper();
        try {
            sceneRouteMap = mapper.readValue(file, new TypeReference<HashMap<String, SceneRoute>>() {});
        } catch (IOException e) {
            System.err.println("Error trying to map");
        }
    }

    public static void goTo(String label) {
        goTo(label, false);
    }

    public static void goTo(String label, Boolean stackUp) {
        if (stackUp){
            sceneStack.push(currentSceneRoute);
        } else {
            sceneStack.clear();
            if(currentSceneRoute != null) {
                currentSceneRoute.setScene(null); // Allows garbage to do its job
            }
        }

        var sceneRoute = sceneRouteMap.get(label);
        loadScene(sceneRoute);
        currentSceneRoute = sceneRoute;
        showStage(sceneRoute);
    }

    public static void back() {
        try {
            var sceneRoute =  sceneStack.pop();
            currentSceneRoute = sceneRoute;
            showStage(sceneRoute);
        } catch (IndexOutOfBoundsException  e) {
            System.err.println("There's no history to get back. Maybe `stackUp` should be `true` for the last call to the `show` method.");
        }
    }

    public static void printSceneRouteMap() {
        sceneRouteMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private static void loadScene(SceneRoute sceneRoute) {
        try {
            var node = FXMLLoader.load(new Object() {}.getClass().getResource(fxmlSource + sceneRoute.getPath()));
            var scene = new Scene((Pane) node);
            sceneRoute.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error trying to load fxml");
            e.printStackTrace();
        }
    }

    private static void showStage(SceneRoute sceneRoute) {
        primaryStage.setScene(sceneRoute.getScene());
        primaryStage.setTitle(sceneRoute.getTitle() + sufTitle);
        primaryStage.show();
    }


}

@Data
@NoArgsConstructor
class SceneRoute {
    private String path;
    private String title;
    @JsonIgnore
    private Scene scene;
}