package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Definindo ícone do app
        Image image = new Image(getClass().getResourceAsStream("view/images/isotypeBlue.png"));
        stage.getIcons().add(image);

        //Definindo configurações padão do "Roteador"
        Router.bind(stage, "/app/view/", "Garage Inc.");

        //Definindo o que seriam "rotas"
        Router.mapping("home", "EmployeeHome.fxml", "Funcionário");

        Router.mapping("login", "login.fxml", "Login");

        //Chamando uma rota
        Router.show("login");


    }
}
