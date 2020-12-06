package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpChangeSuccessfulController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Address;
import app.data.Customer;
import app.data.address.Country;
import app.data.address.State;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;
import app.service.PostalCodesService;
import app.util.MaskedTextField.MaskedTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Alteração de cliente")
public class CustomerChangeController {
    @FXML
    private TextField fieldName;

    @FXML
    private Text textCpfCnpj;

    @FXML
    private MaskedTextField fieldPhone;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldNumber;

    @FXML
    private TextField fieldComplement;

    @FXML
    private MaskedTextField fieldPostalCode;

    @FXML
    private ImageView postalCodeLoading;

    @FXML
    private TextField fieldNeighborhood;

    @FXML
    private TextField fieldCity;

    @FXML
    private ComboBox<State> comboBoxState;

    @FXML
    private ComboBox<Country> comboBoxCountry;

    private MainMenuController menuController;

    private final Customer customer;

    private final CustomersService customersService;

    private final PostalCodesService postalCodesService;

    public CustomerChangeController() {
        customer = (Customer) Router.getUserData();
        customersService = new CustomersService();
        postalCodesService = new PostalCodesService();
    }

    /**
     * The initialize method receive data from {@link CustomerDetailsController} the type {@link Customer} and insert data the {@link Customer} into respective fields.
     */
    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
        comboBoxCountry.getItems().addAll(Country.values());

        var address = customer.getAddress();
        fieldName.setText(customer.getName());
        textCpfCnpj.setText(customer.getCpfCnpj());
        fieldPhone.setPlainText(customer.getPhone());
        fieldAddress.setText(address.getStreet());
        fieldNumber.setText(address.getNumber());
        fieldComplement.setText(address.getComplement());
        fieldPostalCode.setPlainText(address.getPostalCode());
        fieldNeighborhood.setText(address.getNeighborhood());
        fieldCity.setText(address.getCity());
        comboBoxState.setValue(address.getState());
        comboBoxCountry.setValue(address.getCountry());
    }

    /**
     * This method search for a valid postal code using {@link PostalCodesService}.
     */
    public void handleTypedPostalCode() {
        if(fieldPostalCode.getPlainText().length() >= 8) {
            postalCodeLoading.setVisible(true);
            var task = new Task<Void>() {
                @Override
                protected Void call() {
                    try {
                        var address = postalCodesService.search(fieldPostalCode.getPlainText());
                        if(address != null) {
                            Platform.runLater(() -> {
                                fieldCity.setText(address.getCity());
                                fieldNeighborhood.setText(address.getNeighborhood());
                                comboBoxState.setValue(address.getState());
                                comboBoxCountry.setValue(Country.BRAZIL);
                            });
                        }
                    } catch (ConnectionFailureException e) {
                        Router.showPopUp(PopUpServerCloseController.class, 2);
                    }
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    postalCodeLoading.setVisible(false);
                }
            };
            new Thread(task).start();
        }
    }

    /**
     * This method make it visible true or false the comboBoxState
     */
    public void handleSelectedCountry() {
        comboBoxState.getItems().clear();
        var states = comboBoxCountry.getValue().getStates();
        if(states != null) {
            comboBoxState.getItems().addAll(states);
            comboBoxState.setDisable(false);
        } else {
            comboBoxState.setValue(null);
            comboBoxState.setDisable(true);
        }
    }

    /**
     * This method use the service {@link CustomersService} to update customer information.
     */
    public void handleUpdate() {
        var address = Address.builder()
                .street(fieldAddress.getText())
                .number(fieldNumber.getText())
                .complement(fieldComplement.getText())
                .postalCode(fieldPostalCode.getPlainText())
                .neighborhood(fieldNeighborhood.getText())
                .city(fieldCity.getText())
                .state(comboBoxState.getValue())
                .country(comboBoxCountry.getValue())
                .build();
        var customerUpdated = Customer
                .builder()
                .name(fieldName.getText())
                .cpfCnpj(customer.getCpfCnpj())
                .phone(fieldPhone.getPlainText())
                .address(address)
                .build();
        try {
            var c = customersService.update(customer.getId(), customerUpdated);
            if (c != null) {
                Router.showPopUp(PopUpChangeSuccessfulController.class, 3);
                Router.goTo(CustomerDetailsController.class, c, null); //TODO create destine page
            } else {
                System.out.println("Não foi possivel atualizar"); //TODO create a pop-up
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}