package app.controller;


import app.router.RouteMapping;
import app.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@RouteMapping(title = "Página inicial")
public class HomeController extends ApplicationController {

    @FXML
    private Label labelWelcomeMessage;

    public void initialize() {
        var user = AuthenticationService.claimUser();
        labelWelcomeMessage.setText("Bem-vindo " + user.getName());
    }
}
