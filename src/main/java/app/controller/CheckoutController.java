package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PlateNotFoundController;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@RouteMapping(title = "Realizar Checkout")
public class CheckoutController {
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

    public void handleCheckout(ActionEvent actionEvent) {
        try {
            var order = service.findOpenByLicensePlate(fieldLicensePlate.getText());
            if (order != null) {
                Router.goTo(CheckOutConfirmationController.class, order, true);
            } else {
                Router.showPopUp(PlateNotFoundController.class);
            }
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }
}
