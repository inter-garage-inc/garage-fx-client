
package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;

@RouteMapping(title = "Gestão de Serviços")
public class ServiceManagementController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");
    }

}