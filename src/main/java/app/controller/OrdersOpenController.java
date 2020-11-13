package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping(title = "Contas abertas")
public class OrdersOpenController extends ApplicationController {

    @FXML
    private Button customerMonthly;

    @FXML
    private Button customerSingle;

    public void ordersOpenMonthly() {
        Router.goTo(MonthlyOrdersOpenController.class, true);
    }

    public void ordersOpenSingle() {
        Router.goTo("singleordersopen", true);
    }
}
