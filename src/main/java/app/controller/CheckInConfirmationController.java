package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;

@RouteMapping
public class CheckInConfirmationController {

    public Button btnAlter;
    public Button btnSave;
    public Button btnPay;

    public void handleOnActionButtonBtnAlter() {
        Router.goTo(ServiceChangeController.class, true);
    }

    public void handleOnActionButtonBtnSave() throws InterruptedException {
        System.out.println("Check In realizado com sucesso");//TODO Criar pop up
        Thread.sleep(1000);
        Router.goTo(HomeController.class);
    }

    public void handleOnActionButtonBtnPay() throws InterruptedException {
        System.out.println("Carregando tela de Check Out"); //TODO criar um pop up para carregar a tela de check out
        Thread.sleep(1000);
        Router.goTo(CheckOutConfirmationController.class, true);
    }

}
