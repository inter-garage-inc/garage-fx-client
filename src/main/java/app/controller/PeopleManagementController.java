package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.user.Role;
import app.router.RouteMapping;
import app.router.Router;
import app.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@RouteMapping(title = "Gestão de Pessoal")
public class PeopleManagementController {

    public Button btnRegistration;
    public Button btnSrc;
    public TextField txtUsername;
    public Label lblMessage;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
    }

    public void handleOnActionButtonBtnSrc() {
        UserService service = new UserService();

        try {
            var user = service.findByUsername(txtUsername.getText());

            if (user == null) {
                lblMessage.setText("Usuário não encontrado");
                return;
            }

            if(user.getRole() != Role.EMPLOYEE) {
                lblMessage.setText("Permissão negada");
            } else {
                Router.goTo(AlterDeletUserController.class, user);
            }


        } catch (ConnectionFailureException e) {
            //TODO Criar pop up
        }
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(UserRegistrationController.class, true);
    }



}
