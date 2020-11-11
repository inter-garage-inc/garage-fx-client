package app.controller;

import app.Router;
import app.data.Credentials;
import app.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        var authRequest = Credentials.builder()
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .build();

        var authResponse = authenticationService.login(authRequest);

        if(authResponse == Boolean.TRUE) {
            showMessage("Logado com sucesso");
            Router.goTo("home");
            Router.reOpenEffect();
        } else if(authResponse == Boolean.FALSE) {
            showMessage("Login ou senha inválidos");
        } else {
            showMessage("Incapaz de contatar o servidor");
        }
    }

}
