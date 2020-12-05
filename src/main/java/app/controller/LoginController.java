package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpServerCloseController;
import app.data.authentication.Credentials;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@RouteMapping(title = "Identifique-se")
public class LoginController {

    @FXML
    private TextField fieldUsername;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Label labelMessage;

    private AuthenticationService service;

    public LoginController() {
        service = new AuthenticationService();
    }

    private void showMessage(String message) {
        labelMessage.setVisible(true);
        labelMessage.setText(message);
    }

    public void handleLogin() {
        var credentials = Credentials.builder()
                .username(fieldUsername.getText().trim())
                .password(fieldPassword.getText().trim())
                .build();
        try {
            var response = service.login(credentials);
            if(response) {
                showMessage("Logado com sucesso");
                Router.goTo(HomeController.class);
                Router.reOpenEffect();
            } else {
                showMessage("Usuário ou senha inválidos");
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}
