package app.controller;


import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@RouteMapping(title = "Página inicial")
public class HomeController {

    @FXML
    private Label labelWelcomeMessage;

    @FXML
    MainMenuController mainMenuController;

    public void initialize() {
        var user = AuthenticationService.claimUser();
        labelWelcomeMessage.setText("Bem-vindo " + user.getName());
    }
}
