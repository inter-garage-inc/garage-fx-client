package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Checkout")
public class CheckoutController {

    public Button btnGet;

    public void handleOnActionButtonBtnGet() throws InterruptedException {
        Router.goTo(CheckOutConfirmationController.class, true);
        System.out.println("buscando...");
        Thread.sleep(2000);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnCheckout.getStyleClass().add("button-menu-selected");
    }
}
