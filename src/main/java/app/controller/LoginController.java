package app.controller;

import app.data.authentication.Credentials;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import app.service.ConnectionFailureException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private AuthenticationService authenticationService;

    public void initialize() throws JsonProcessingException {
        authenticationService = new AuthenticationService();
    }

    private void showMessage(String message) {
        labelMessage.setVisible(true);
        labelMessage.setText(message);
    }

    public void handleButtonLogin() {
        var credentials = Credentials.builder()
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .build();
        try {
            var response = authenticationService.login(credentials);
            if(response) {
                showMessage("Logado com sucesso");
                Router.goTo(HomeController.class);
                Router.reOpenEffect();
            } else {
                showMessage("Login ou senha inválidos");
            }
        } catch (ConnectionFailureException e) {
            showMessage("Incapaz de contatar o servidor");
        }
    }

}
