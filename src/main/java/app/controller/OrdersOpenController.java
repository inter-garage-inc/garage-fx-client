package app.controller;

import app.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OrdersOpenController extends ApplicationController {

    @FXML
    private Button customerMonthly;

    @FXML
    private Button customerSingle;

    public void ordersOpenMonthly() {
        Router.goTo("monthlyordersopen", true);
    }

    public void ordersOpenSingle() {
        Router.goTo("singleordersopen", true);
    }
}
