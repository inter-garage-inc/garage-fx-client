package app.controller.popup;

import app.controller.OrdersOpenController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@RouteMapping(title = "Placa Não Encontrada", popup = true)
public class PlateNotFoundController {
    @FXML
    private void handleClose(ActionEvent actionEvent) {
        Router.closePopUp();
    }

    @FXML
    private void handleGoToOrdersOpen(ActionEvent actionEvent) {
        Router.goTo(OrdersOpenController.class);
    }
}
