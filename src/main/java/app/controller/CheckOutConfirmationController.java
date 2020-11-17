package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;

@RouteMapping
public class CheckOutConfirmationController {

    public Button btnPay;

    public void handleOnActionButtonBtnPay() throws InterruptedException {
        System.out.println("PAGO"); //TODO Pago ??
        Thread.sleep(1000); //TODO Sleep
        Router.goTo(HomeController.class);
    }
}
