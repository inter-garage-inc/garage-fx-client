package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping( title = "Contas abertas | Mensalistas")
public class CustomerOrdersOpenController {

    public Button btnSelect;

    public void handleOnActionButtonBtnSelect() {
        Router.goTo(CheckOutConfirmationController.class, true);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnOpenAccounts.getStyleClass().add("button-menu-selected");
    }
}
