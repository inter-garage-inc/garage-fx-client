package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.user.Role;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import app.service.UsersService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-22
 */

@RouteMapping(title = "Gestão de Pessoal")
public class PeopleManagementController {

    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnSrc;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblMessage;

    private MainMenuController menuController;

    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
    }

    /**
     * This method search {@link app.data.User} by username using {@link UsersService} to alter or delete {@link app.data.User}
     */
    public void handleOnActionButtonBtnSrc() {
        UsersService service = new UsersService();

        try {
            var user = service.findByUsername(txtUsername.getText());

            if (user == null) {
                lblMessage.setText("Usuário não encontrado");
                return;
            }

            var logado = AuthenticationService.claimUser();
            Boolean permission = (logado.getRole().equals(Role.MANAGER))
                    && (user.getRole().equals(Role.ADMIN)
                    || user.getRole().equals(Role.MANAGER)
            );

            Boolean atual = logado.getId().equals(user.getId());

            if(atual) {
                lblMessage.setText("Não pode editar o usuário atual");
                return;
            }

            if(permission) {
                lblMessage.setText("Permissão negada");
            } else {
                Router.goTo(AlterDeletUserController.class, user, true);
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    /**
     * This method call {@link UserRegistrationController} using {@link Router}
     */
    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(UserRegistrationController.class, true);
    }
}
