
package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Gestão de Serviços")
public class ServiceManagementController {

    public Button btnRegistration;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(ServiceRegistrationController.class, true);
    }

}