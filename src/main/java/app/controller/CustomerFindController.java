package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Buscar Cliente Mensalista")
public class CustomerFindController {

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }

    public Button btnSrc;

    public void handleOnActionButtonBtnSrc() {
        Router.goTo(MonthlyFoundController.class,true);
    }

}
