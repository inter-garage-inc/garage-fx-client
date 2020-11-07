package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    private static Stage stage;
    private static String sufTitle;
    private static String fxmlSource;
    private static HashMap<String, SceneRoute> SceneRouteMap = new HashMap<>();

    private static class SceneRoute {
        private String path;
        private String title;

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
        SceneRouteMap.put(label, new SceneRoute(path, title));
    }

    public static void show(String label) throws IOException {
        SceneRoute sceneRoute = SceneRouteMap.get(label);
        stage.setScene(loadScene(sceneRoute));
        stage.setTitle(sceneRoute.title + " - " + sufTitle);
        stage.show();
    }

    private static Scene loadScene(SceneRoute sceneRoute) throws IOException {
        Parent node = FXMLLoader.load(new Object() {}.getClass().getResource(fxmlSource + sceneRoute.path));
        return new Scene(node);
    }
}
