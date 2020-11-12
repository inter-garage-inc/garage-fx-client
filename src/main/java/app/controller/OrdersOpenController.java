package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping
public class OrdersOpenController extends ApplicationController {

    @FXML
    private Button customerMonthly;

    public void ordersOpenMonthly() {
        Router.goTo(MonthlyOrdersOpenController.class, true);
    }
}
