package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.controller.popup.PopUpSucessCheckOutController;
import app.data.Order;
import app.data.order.PaymentMethod;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;

@RouteMapping(title = "Confirmação de Checkout")
public class CheckOutConfirmationController {

    public Button btnPay;
    public Label lblLicensePlate;
    public Label lblTot;
    public AnchorPane anchorPane;
    public ComboBox<PaymentMethod> cbStatus;
    public Label lblMessage;
    private Order order;
    private BigDecimal totalAmount;
    private OrdersService ordersService;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        ordersService = new OrdersService();
        cbStatus.getItems().addAll(PaymentMethod.values());
        menuController.btnCheckout.getStyleClass().add("button-menu-selected");
        order = (Order) Router.getUserData();

        var layoutY = 5.0;
        var layoutX = 5.0;
        for (var item : order.getItems()) {
            if(item.getCatalog() != null) {
                lblLicensePlate.setText(item.getCatalog().getDescription());
            }
            var label = new Label();
            label.setText(" ⌂ " + item.getCatalog().getDescription());
            label.getStyleClass().add("label");
            label.setLayoutY(layoutY);
            label.setLayoutX(layoutX);
            layoutY += 20;
            anchorPane.getChildren().add(label);
            totalAmount = totalAmount.add(item.getCatalog().getPrice());
        }
        lblLicensePlate.setText(order.getLicensePlate());
        lblTot.setText("R$" + totalAmount);
    }

    public CheckOutConfirmationController() {
        totalAmount = new BigDecimal(0);
    }

    public void handleOnActionButtonBtnPay() {
        try {
            var orderBuilder = Order.builder()
                    .licensePlate(lblLicensePlate.getText())
                    .paymentMethod(cbStatus.getValue())
                    .build();

            var response = ordersService.close(orderBuilder);
            if(response != null) {
                Router.showPopUp(PopUpSucessCheckOutController.class);
            } else {
                lblMessage.setText("Não foi possível realizar o checkout");
            }
            Router.goTo(HomeController.class);
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class);
        }
    }
}
