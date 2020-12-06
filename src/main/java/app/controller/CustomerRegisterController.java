package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpRegisterSuccessfulController;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Cadastrar Novo Cliente")
public class CustomerRegisterController {

    @FXML
    private Label lblMessage;

    @FXML
    private TextField fieldName;

    @FXML
    private MaskedTextField fieldCpfCnpj;

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

    private CustomersService customersService;

    private PostalCodesService postalCodesService;

    public CustomerRegisterController() {
        customersService = new CustomersService();
        postalCodesService = new PostalCodesService();
    }

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
        comboBoxCountry.getItems().addAll(Country.values());
    }

    /**
     * This method add mask the characters from CPF
     */
    public void handleOnActionRadioButtonCpf() {
        fieldCpfCnpj.setMask("###.###.###-##");
    }

    /**
     * This method add mask the characters from CNPJ
     */
    public void handleOnActionRadioButtonCnpj() {
        fieldCpfCnpj.setMask("##.###.###/####-##");
    }

    /**
     * This method make it visible true or false the comboBoxState.
     */
    public void handleOnActionComboBoxCountry() {
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
     * This method use the service {@link PostalCodesService} to search the postal code and insert data into respective fields.
     */
    public void handleOnKeyReleasedFieldPostalCode() {
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
     * This method use the service {@link CustomersService} to save a new customer.
     */
    public void handleOnActionButtonRegister() {
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
        var customer = Customer
                .builder()
                .name(fieldName.getText())
                .cpfCnpj(fieldCpfCnpj.getPlainText())
                .phone(fieldPhone.getPlainText())
                .address(address)
                .build();
        try {
            var c = customersService.save(customer);
            if (c != null) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 3);
                Router.goTo(CustomerDetailsController.class, c); //TODO create destine page
            } else {
                lblMessage.setText("Preencha todas as colunas.");
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}