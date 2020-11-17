package app.controller;

import app.controller.popup.PopUpAlterSuccessfulController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;

@RouteMapping(title = "Alteração de Serviços")
public class ServiceChangeController  {

    public Button btnSave;

    public void handleOnActionButtonSave() throws InterruptedException {
        Router.goTo(PopUpAlterSuccessfulController.class);
        Thread.sleep(1500);
        Router.goTo(CheckInConfirmationController.class);
    }
}
