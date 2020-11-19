package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.data.Address;
import app.data.Customer;
import app.data.address.Country;
import app.data.address.State;
import app.data.address.state.Brazil;
import app.router.RouteMapping;
import app.router.Router;
import app.service.ConnectionFailureException;
import app.service.CustomerService;
import app.service.PostalCodeService;
import app.util.MaskedTextField.MaskedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;

@RouteMapping(title = "Novo Cliente")
public class CustomerRegisterController {

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
    private TextField fieldNeighborhood;

    @FXML
    private TextField fieldCity;

    @FXML
    private ComboBox<State> comboBoxState;

    @FXML
    private ComboBox<Country> comboBoxCountry;

    private CustomerService customerService;

    private PostalCodeService postalCodeService;

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        customerService = new CustomerService();
        postalCodeService = new PostalCodeService();
        initComboBoxes();
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }

    private void initComboBoxes() {
        comboBoxCountry.getItems().addAll(Country.values());
    }

    @FXML
    private void handleOnActionComboBoxCountry(ActionEvent actionEvent) {
        comboBoxState.getItems().clear();
        var states = comboBoxCountry.getValue().getStates();
        if(states == null) {
            comboBoxState.setDisable(true);
        } else {
            comboBoxState.setDisable(false);
            comboBoxState.getItems().addAll(states);
        }
    }

    @FXML
    private void handleOnActionRadioButtonCpf(ActionEvent actionEvent) {
        fieldCpfCnpj.setMask("###.###.###-##");
    }

    @FXML
    private void handleOnActionRadioButtonCnpj(ActionEvent actionEvent) {
        fieldCpfCnpj.setMask("##.###.###/####-##");
    }

    @FXML
    private void handleOnKeyReleasedFieldPostalCode(KeyEvent keyEvent) {
        if(fieldPostalCode.getPlainText().length() >= 8) {
            try {
                var address = postalCodeService.search(fieldPostalCode.getText());
                if(address != null) {
                    fieldCity.setText(address.getCity());
                    fieldNeighborhood.setText(address.getNeighborhood());
                    comboBoxState.setValue(address.getState());
                    comboBoxCountry.setValue(Country.BRAZIL);
                }
            } catch (ConnectionFailureException e) {
                System.err.println("Error using postal code service");
            }
        }
    }

    @FXML
    private void handleOnActionButtonRegister() {
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
            var response = customerService.save(customer);
            if (response) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class); //TODO set time to close
//                Router.goTo(); TODO set destination go to after
                Router.closePopUp(1);
                Router.goTo(HomeController.class);
                Router.goTo(MonthlyFoundController.class, true);
            } else {
                System.out.println("Não foi possivel cadastrar"); //TODO create a pop-up
            }
        } catch (ConnectionFailureException e) {
            System.err.println("Error using customer service");
        }
    }
}
