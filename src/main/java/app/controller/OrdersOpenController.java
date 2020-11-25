package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;

@RouteMapping(title = "Contas abertas")
public class OrdersOpenController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnOpenAccounts.getStyleClass().add("button-menu-selected");
    }
}
