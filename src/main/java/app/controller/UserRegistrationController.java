package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.data.User;
import app.data.user.Role;
import app.data.user.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.client.ConnectionFailureException;
import app.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@RouteMapping(title = "Cadastro de Usuário")
public class UserRegistrationController {

    public TextField fieldName;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public PasswordField fieldConfPassword;
    public Button btnSave;
    public Label lblError;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
    }

    public void handleOnActionButtonBtnSave() {
        Boolean confirmPassword = fieldPassword.getText().equals(fieldConfPassword.getText());
        var user = User.builder()
                .name(fieldName.getText())
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .role(Role.EMPLOYEE)
                .status(Status.ACTIVE)
                .build();
        try {
        if(confirmPassword) {
            UserService service = new UserService();
            if(service.userSave(user)) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 1);
                lblError.setVisible(false);
            } else {
                lblError.setText("Username ja existente");
                fieldUsername.setText("");
            }

            } else {
                lblError.setText("Senhas não coincidem");
            }
        } catch (ConnectionFailureException e) {
            //TODO criar pop up
        }
    }
}
