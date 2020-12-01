package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.CheckInConfirmController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Order;
import app.data.order.Item;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

@RouteMapping(title = "Confirmação de Check In")
public class CheckInConfirmationController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private Button btnAlter;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnPay;

    @FXML
    private Label lblVehicle;

    @FXML
    private Label lblLicensePlate;

    @FXML
    private Label lblServices;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblParkingSpace;

    private Order order;

    private OrdersService ordersService;

    private CheckInConfirmationController() {
        order = (Order) Router.getUserData();
        ordersService = new OrdersService();
    }

    @FXML
    private void initialize() {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        var layoutY = 5.0;
        var layoutX = 5.0;
        for (var item : order.getItems()) {
            if(item.getParking() != null) {
                lblParkingSpace.setText(item.getParking().getParkingSpace().getCode());
            }
            var label = new Label();
            label.setText(" ⌂ " + item.getDescription());
            label.getStyleClass().add("label");
            label.setLayoutY(layoutY);
            label.setLayoutX(layoutX);
            layoutY += 20;
            anchorPane.getChildren().add(label);
        }

        lblLicensePlate.setText(order.getLicensePlate());
    }

    @FXML
    private void handleOnActionButtonBtnSave() {
        try {
            var response = ordersService.save(order);
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
    private void handleOnActionButtonBtnAlter() {
        Router.goTo(ChangeCheckInController.class, true);
    }
}
