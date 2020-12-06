package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.order.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-12-01
 */
@RouteMapping(title = "Realizar Checkout")
public class CheckoutController {

    @FXML
    public Label lblMessage;

    @FXML
    private TextField fieldLicensePlate;

    private MainMenuController menuController;

    private OrdersService service;

    public CheckoutController() {
        service = new OrdersService();
    }

    public void initialize() {
        menuController.btnCheckout.getStyleClass().add("button-menu-selected");
    }

    /**
     * This method use the service {@link OrdersService} to search a license plate with check in open and call {@link CheckOutConfirmationController} to save like pay
     */
    public void handleOnActionBtnCheckout() {
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
