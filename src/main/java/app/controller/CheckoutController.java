package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpPlateNotFoundController;
import app.data.order.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@RouteMapping(title = "Realizar Checkout")
public class CheckoutController {
    public Label lblMessage;
    @FXML
    private MainMenuController menuController;

    @FXML
    private TextField fieldLicensePlate;

    private OrdersService service;

    public CheckoutController() {
        service = new OrdersService();
    }

    public void initialize() {
        menuController.btnCheckout.getStyleClass().add("button-menu-selected");
    }

    public void handleOnActionBtnCheckout(ActionEvent actionEvent) {
        try {
            var order = service.findOpenByLicensePlate(fieldLicensePlate.getText());

            Boolean orderNull = order != null;
            if(!orderNull) {
                lblMessage.setText("Essa placa não esta com check in aberto");
                return;
            }
            Boolean orderOpen = order.getStatus().equals(Status.OPEN);
            if(!orderOpen) {
                lblMessage.setText("O campo precisa ser preenchido");
                return;

            }

            Router.goTo(CheckOutConfirmationController.class, order, true);
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }
}
