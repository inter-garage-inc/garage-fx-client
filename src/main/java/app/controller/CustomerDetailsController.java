package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.PopUpCustomerDeleteController;
import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

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

    /**
     * The initialize method receive data the {@link Customer} from {@link CustomerSearchController} and insert the data {@link Customer} into respective fields.
     */
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
            textCurrentPlan.setText(vehicle.getPlan().getName() + "\n(" + vehicle.getPlan().getType() +")");
            textLicensePlate.setText(vehicle.getLicencePlate());
        }
    }

    /**
     * This method call {@link PopUpCustomerDeleteController} using {@link Router}
     */
    public void handleDeleteCustomer() {
        Router.showPopUp(PopUpCustomerDeleteController.class, customer);
    }

    /**
     * This method call {@link CustomerChangeController} using {@link Router}
     */
    public void handleChangeCustomer() {
        Router.goTo(CustomerChangeController.class, customer, true);
    }

    /**
     * this method call {@link VehicleRegisterController} using {@link Router}
     */
    public void handleNewVehicle() {
        Router.goTo(VehicleRegisterController.class, customer, true);
    }
}