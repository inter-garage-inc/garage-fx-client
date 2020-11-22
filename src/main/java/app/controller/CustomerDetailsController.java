package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.CustomerDeleteController;
import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@RouteMapping(title = "Cliente")
public class CustomerFoundController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private Label labelName;

    @FXML
    private Label labelCpfCnpj;

    @FXML
    private Label fieldPhone;

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelNumber;

    @FXML
    private Label labelComplement;

    @FXML
    private Label labelPostalCode;

    @FXML
    private Label labelNeighborhood;

    @FXML
    private Label labelCity;

    @FXML
    private Label labelState;

    @FXML
    private Label labelCountry;

    private Customer customer;

    public CustomerFoundController () {
        customer = (Customer) Router.getUserData();
    }

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");

        var address = customer.getAddress();
        labelName.setText(customer.getName());
        labelCpfCnpj.setText(customer.getCpfCnpj());
        fieldPhone.setText(customer.getPhone());
        labelAddress.setText(address.getStreet());
        labelNumber.setText(address.getNumber());
        labelComplement.setText(address.getComplement());
        labelPostalCode.setText(address.getPostalCode());
        labelNeighborhood.setText(address.getNeighborhood());
        labelCity.setText(address.getCity());
        labelState.setText(address.getState().getValue());
        labelCountry.setText(address.getCountry().getValue());
    }

    @FXML
    private void handleOnActionDelete(ActionEvent actionEvent) {
        Router.showPopUp(CustomerDeleteController.class, customer);
    }
}
