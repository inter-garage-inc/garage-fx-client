package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.user.Role;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
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

            var logado = AuthenticationService.claimUser();
            Boolean permission = (
                    logado.getRole().equals(Role.MANAGER))
                    && (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MANAGER)
            );

            if(permission) {
                lblMessage.setText("Permissão negada");
            } else {
                Router.goTo(AlterDeletUserController.class, user);
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(UserRegistrationController.class, true);
    }
}
