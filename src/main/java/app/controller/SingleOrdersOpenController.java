package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

@RouteMapping(title = "Ordens de Serviço Abertas")
public class SingleOrdersOpenController {

    public Button btnSrc;
    public TableView tbView;

    public void handleOnActionButtonBtnSelect() {
        Router.goTo(CheckOutConfirmationController.class, true);
    }

}