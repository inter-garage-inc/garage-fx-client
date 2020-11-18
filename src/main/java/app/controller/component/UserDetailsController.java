package app.controller.component;

import app.controller.LoginController;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class UserDetailsController {
    @FXML
    private Label labelName;

    @FXML
    private void initialize() {
        var user = AuthenticationService.claimUser();
        labelName.setText(user.getName());
    }

    public void handleOnActionButtonLogout(ActionEvent actionEvent) {
        AuthenticationService.logout();
        Router.goTo(LoginController.class);
        Router.reOpenEffect();
    }
}
