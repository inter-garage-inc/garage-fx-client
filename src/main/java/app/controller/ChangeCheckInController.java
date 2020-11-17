
package app.controller;

import app.controller.popup.PopUpAlterSuccessfulController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;

@RouteMapping(title = "Alteração no Check in")
public class ChangeCheckInController {

    public Button btnSave;

    public void handleOnActionButtonSave() throws InterruptedException {
        Router.showPopUp(PopUpAlterSuccessfulController.class);
        Router.closePopUp(2);
        Router.goTo(CheckInConfirmationController.class);
    }
}