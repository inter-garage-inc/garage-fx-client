package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;

@RouteMapping
public class MonthlyFoundController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }
}
