
package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.PopUpChangeSuccessfulController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Alteração no Check in")
public class ChangeCheckInController {

    public Button btnSave;

    public void handleOnActionButtonSave() throws InterruptedException {
        Router.showPopUp(PopUpChangeSuccessfulController.class);
        Router.closePopUp(2);
        Router.goTo(CheckInConfirmationController.class);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");
    }
}