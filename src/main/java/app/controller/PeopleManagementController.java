package app.controller;

import app.controller.component.MainMenuController;
import app.data.User;
import app.router.RouteMapping;
import app.router.Router;
import app.service.ConnectionFailureException;
import app.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

@RouteMapping(title = "Gestão de Pessoal")
public class PeopleManagementController {

    public Button btnRegistration;
    public Button btnSrc;
    public TextField txtUsername;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
    }

    public void handleOnActionButtonBtnSrc() {
        UserService service = new UserService();

        try {
            var user = service.findByUsername(txtUsername.getText());
            System.out.println(user);

            if (user != null) {
                Router.goTo(AlterDeletUserController.class, user);
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (ConnectionFailureException e) {
            //TODO Criar pop up
        }
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(UserRegistrationController.class, true);
    }



}
