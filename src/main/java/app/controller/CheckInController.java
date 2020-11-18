package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Check in")
public class CheckInController {

    @FXML public Button btnOk;

    @FXML private MainMenuController menuController;

    public void handleOnActionButtonOk(ActionEvent actionEvent) {
        Router.goTo(CheckInConfirmationController.class, true);
    }

    public void initialize() {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");
    }

}
