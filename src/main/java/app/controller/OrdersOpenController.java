package app.controller;

import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;

@RouteMapping(title = "Contas abertas")
public class OrdersOpenController {

    public Button btnCustomerMonthly;
    public Button btnCustomerSingle;

    public void handleOnActionButtonBtnCustomerMonthly() {
        Router.goTo(MonthlyOrdersOpenController.class, true);
    }

    public void handleOnActionButtonBtnCustomerSingle() {
        Router.goTo(SingleOrdersOpenController.class, true);
    }
}
