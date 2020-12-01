package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;

@RouteMapping(title = "Cadastro de Planos")
public class PlanRegisterController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");
    }
}