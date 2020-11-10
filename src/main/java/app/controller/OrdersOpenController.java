package app.controller;

import app.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OrdersOpenController extends ApplicationController {

    @FXML
    private Button customerMonthly;

    public void ordersOpenMonthly() {
        Router.show("orderopenmonthly", true);
    }
}
