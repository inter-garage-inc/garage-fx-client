package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Router {
    private static Stage stage;
    private static String sufTitle;
    private static String fxmlSource;
    private static final HashMap<String, SceneRoute> sceneRouteMap = new HashMap<>();
    private static final Stack<SceneRoute> sceneStack = new Stack<>();
    private static SceneRoute currentSceneRoute;

    private static class SceneRoute {
        private final String path;
        private final String title;
        private Scene scene;

        private SceneRoute(String path, String title) {
            this.path = path;
            this.title = title;
        }
    }

    public static void bind(Stage stage, String fxmlSource, String sufTitle) {
        Router.stage = stage;
        Router.sufTitle = sufTitle;
        Router.fxmlSource = fxmlSource;
    }

    public static void mapping(String label, String path, String title) {
        sceneRouteMap.put(label, new SceneRoute(path, title));
    }

    public static void show(String label) {
        show(label, false);
    }

    public static void show(String label, Boolean stackUp) {
        var sceneRoute = sceneRouteMap.get(label);

        try {
            sceneRoute.scene = loadScene(sceneRoute);
        } catch (IOException e) {
            System.err.println("Error when trying to load fxml");
            e.printStackTrace();
        }

        if (stackUp){
            sceneStack.push(currentSceneRoute);
        } else {
            sceneStack.clear();
            if(currentSceneRoute != null) {
                currentSceneRoute.scene = null; // Allows garbage to do its job
            }
        }
        currentSceneRoute = sceneRoute;
        setStage(sceneRoute.scene, sceneRoute.title);
    }

    public static void back() {
        try {
            SceneRoute sceneRoute =  sceneStack.pop();
            currentSceneRoute = sceneRoute;
            setStage(sceneRoute.scene, sceneRoute.title);
        } catch (IndexOutOfBoundsException  e) {
            System.err.println("There's no history to get back. Maybe `stackUp` should be `true` for the last call to the `show` method.");
            e.printStackTrace();
        }
    }

    private static Scene loadScene(SceneRoute sceneRoute) throws IOException {
        var pane = FXMLLoader.load(new Object() {}.getClass().getResource(fxmlSource + sceneRoute.path));
        return new Scene((Pane) pane);
    }

    private static void setStage(Scene scene, String title) {
        stage.setScene(scene);
        stage.setTitle(title + " — " + sufTitle);
        stage.show();
    }

}
