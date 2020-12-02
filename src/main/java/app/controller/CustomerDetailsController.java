package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.CustomerDeleteController;
import app.data.Customer;
import app.data.Plan;
import app.data.Vehicle;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

@RouteMapping(title = "Detalhes do Cliente")
public class CustomerDetailsController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private Text textName;

    @FXML
    private Text textCpfCnpj;

    @FXML
    private Text textPhone;

    @FXML
    private Text textAddress;

    @FXML
    private Text textNumber;

    @FXML
    private Text textComplement;

    @FXML
    private Text textPostalCode;

    @FXML
    private Text textNeighborhood;

    @FXML
    private Text textCity;

    @FXML
    private Text textState;

    @FXML
    private Text textCountry;

    @FXML
    public Text textLicensePlate;

    @FXML
    public Text textCurrentPlan;

    private Customer customer;

    public CustomerDetailsController() {
        customer = (Customer) Router.getUserData();
    }

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");

        var address = customer.getAddress();
        textName.setText(customer.getName());
        textCpfCnpj.setText(customer.getCpfCnpj());
        textPhone.setText(customer.getPhone());
        textAddress.setText(address.getStreet());
        textNumber.setText(address.getNumber());
        textComplement.setText(address.getComplement());
        textPostalCode.setText(address.getPostalCode());
        textNeighborhood.setText(address.getNeighborhood());
        textCity.setText(address.getCity());
        textState.setText(address.getState().getValue());
        textCountry.setText(address.getCountry().getValue());
        var vehicle = customer.getVehicle();
        if(vehicle != null) {
            textCurrentPlan.setText(vehicle.getPlan().getName() + "(" + vehicle.getPlan().getType() +")");
            textLicensePlate.setText(vehicle.getLicencePlate());
        }
    }

    @FXML
    private void handleDeleteCustomer(ActionEvent actionEvent) {
        Router.showPopUp(CustomerDeleteController.class, customer);
    }

    @FXML
    private void handleChangeCustomer(ActionEvent actionEvent) {
        Router.goTo(CustomerChangeController.class, customer, true);
    }

    @FXML
    private void handleNewVehicle(ActionEvent actionEvent) {
        Router.goTo(VehicleRegisterController.class, customer, true);
    }
}