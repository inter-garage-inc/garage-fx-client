package app.controller.popup;

import app.controller.CustomerRegisterController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@RouteMapping(title = "Cliente Não Encontrado", popup = true)
public class PopUpCustomerNotFoundController {
    @FXML
    private void handleOnActionClose(ActionEvent actionEvent) {
        Router.closePopUp();
    }

    @FXML
    private void handleOnActionRegister(ActionEvent actionEvent) {
        Router.closePopUp();
        Router.goTo(CustomerRegisterController.class, true);
    }
}
