package app.controller;

import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@RouteMapping(title = "Exclusão de Cliente")
public class CustomerDeleteController {
    private Customer customer;

    private CustomerService service;

    public CustomerDeleteController() {
        customer = (Customer) Router.getUserData();
        service = new CustomerService();
    }

    @FXML
    private void handleOnActionDelete(ActionEvent actionEvent) {
        // TODO create delete method in customer service
    }
}
