package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.CheckInConfirmController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.Order;
import app.data.order.Item;
import app.data.parking.ParkingSpace;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;

@RouteMapping(title = "Confirmação de Check In")
public class CheckInConfirmationController {

    public Button btnAlter;
    public Button btnSave;
    public Button btnPay;
    public Label lblVehicle;
    public Label lblLicensePlate;
    public Label lblServices;
    public AnchorPane anchorPane;
    public OrderService service;
    public Label lblMessage;
    public Label lblParkingSpace;

    public CheckInConfirmationController() {
        service = new OrderService();
    }

    public void handleOnActionButtonBtnAlter() {
        Router.goTo(ChangeCheckInController.class, true);
    }

    public void handleOnActionButtonBtnSave() throws ConnectionFailureException {
        Order order = (Order) Router.getUserData();



        try {
            var response = service.ordersSave(order);
            if(response) {
                Router.showPopUp(CheckInConfirmController.class, 2);
                Router.goTo(HomeController.class);
            } else {
                lblMessage.setText("Não foi possível realizar o check in");
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    public void handleOnActionButtonBtnPay() throws InterruptedException {
        System.out.println("Carregando tela de Check Out"); //TODO criar um pop up para carregar a tela de check out
        Thread.sleep(1000);
        Router.goTo(CheckOutConfirmationController.class, true);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        Order order = (Order) Router.getUserData();
        List<Item> response = order.getItems();

        Double layoutY = 5.0;
        Double layoutX = 5.0;
        ParkingSpace parkingSpace = null;

        for (Item item : response) {
            parkingSpace = item.getParking().getParkingSpace();
            lblLicensePlate.setText(order.getLicensePlate());
            Label label = new Label();
            label.setText(" ⌂ " + item.getDescription());
            label.getStyleClass().addAll("label");
            label.setLayoutY(layoutY);
            label.setLayoutX(layoutX);
            layoutY += 20;
            anchorPane.getChildren().addAll(label);
        }
        lblParkingSpace.setText(parkingSpace.getCode().toString());


        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");
    }

}
