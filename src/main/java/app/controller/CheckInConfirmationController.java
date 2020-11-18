package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping
public class CheckInConfirmationController {

    public Button btnAlter;
    public Button btnSave;
    public Button btnPay;

    public void handleOnActionButtonBtnAlter() {
        Router.goTo(ChangeCheckInController.class, true);
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

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");
    }

}
