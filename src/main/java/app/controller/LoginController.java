package app.controller;

import app.Router;
import app.data.AuthRequest;
import app.data.AuthResponse;
import app.service.AuthenticationService;
import app.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField fieldUsername;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Label labelMessage;

    private AuthenticationService authenticationService;

    public void initialize() {
        authenticationService = new AuthenticationService();
    }

    private void showMessage(String message) {
        labelMessage.setVisible(true);
        labelMessage.setText(message);
    }

    public void handleButtonLogin() {
        var authRequest = AuthRequest.builder()
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .build();
        try {
            var authResponse = authenticationService.login(authRequest);
            //TODO implement real flow
            if(authResponse == null) {
                showMessage("Usuário ou senha inválidos");
            } else {
                showMessage("Logado com sucesso");
                System.out.println("Token de acesso: " + authResponse.getToken());
            }
        } catch (IOException | InterruptedException e) {
            showMessage("Ocorreu um erro inesperado!");
            e.printStackTrace();
        }
    }

}
