package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

@RouteMapping(title = "Ordens de Serviço Abertas")
public class SingleOrdersOpenController {

    public Button btnSrc;
    public TableView tbView;

    public void handleOnActionButtonBtnSelect() {
        Router.goTo(CheckOutConfirmationController.class, true);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnOpenAccounts.getStyleClass().add("button-menu-selected");
    }

}