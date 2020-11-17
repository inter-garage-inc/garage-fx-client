package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

@RouteMapping( title = "Contas abertas | Mensalistas")
public class MonthlyOrdersOpenController {

    public Button btnSelect;

    public void handleOnActionButtonBtnSelect() {
        Router.goTo(CheckOutConfirmationController.class, true);
    }
}
