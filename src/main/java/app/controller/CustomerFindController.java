package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;

@RouteMapping(title = "Buscar Cliente Mensalista")
public class CustomerFindController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }

}
