package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping
public class CheckOutConfirmationController {

    public Button btnPay;

    public void handleOnActionButtonBtnPay() throws InterruptedException {
        System.out.println("PAGO"); //TODO Pago ??
        Thread.sleep(1000); //TODO Sleep
        Router.goTo(HomeController.class);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnCheckout.getStyleClass().add("button-menu-selected");
    }
}
