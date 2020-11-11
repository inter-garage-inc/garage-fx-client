package app.controller;


import app.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController extends ApplicationController {

    @FXML
    private Label labelWelcomeMessage;

    public void initialize() {
        var authenticationService = new AuthenticationService();
        var user = authenticationService.claimUser();
        setWelcomeMessage("Bem-vindo " + user.getName());
    }

    private void setWelcomeMessage(String message) {
        labelWelcomeMessage.setText(message);
    }
}
