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
        stage.setResizable(false);


        //Definindo configurações padão do "Roteador"
        Router.bind(stage, "/app/view/", "Garage Inc.");

        //Definindo o que seriam "rotas"
        Router.mapping("home", "EmployeeHome.fxml", "Funcionário");

        Router.mapping("login", "Login.fxml", "Login");

        Router.mapping("ordersopen", "OrdersOpen.fxml", "Contas abertas");

        Router.mapping("montlhynf", "MontlhyNotFound.fxml", "Mensalista nao encontrado");

        Router.mapping("monthlycustomer", "MonthlyCustomerController.fxml", "Buscar cliente mensalista");


        Router.mapping("orderopenmonthly", "OrdersOpenMonthly.fxml", "Ordens abertas de mensalistas");
        
        Router.mapping( "deletecustomer", "DeleteCustomer.fxml", "Exclusão de cliente");
        //Chamando uma rota
        Router.show("login");


    }
}

