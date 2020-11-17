package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Check in")
public class CheckInController {

    @FXML public Button btnOk;

    public void handleOnActionButtonOk(ActionEvent actionEvent) {
        Router.goTo(CheckInConfirmationController.class, true);
    }
}
