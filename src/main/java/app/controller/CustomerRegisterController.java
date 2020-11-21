package app.controller;

import app.controller.component.MainMenuController;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.data.Address;
import app.data.Customer;
import app.data.address.Country;
import app.data.address.State;
import app.router.RouteMapping;
import app.router.Router;
import app.service.ConnectionFailureException;
import app.service.CustomerService;
import app.service.PostalCodeService;
import app.util.MaskedTextField.MaskedTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

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
    private ImageView postalCodeLoading;

    @FXML
    private TextField fieldNeighborhood;

    @FXML
    public ImageView neighborhoodLoading;

    @FXML
    private TextField fieldCity;

    @FXML
    public ImageView cityLoading;

    @FXML
    private ComboBox<State> comboBoxState;

    @FXML
    public ImageView stateLoading;

    @FXML
    private ComboBox<Country> comboBoxCountry;

    @FXML
    public ImageView countryLoading;

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
            showLoading();
            var taskPostalCode = new Task<Void>() {
                @Override
                public Void call() {
                    try {
                        var address = postalCodeService.search(fieldPostalCode.getText());
                        if(address != null) {
                            Platform.runLater(() -> {
                                fieldCity.setText(address.getCity());
                                fieldNeighborhood.setText(address.getNeighborhood());
                                comboBoxState.setValue(address.getState());
                                comboBoxCountry.setValue(Country.BRAZIL);
                            });
                        }
                    } catch (ConnectionFailureException e) {
                        System.err.println("Error using postal code service");
                    }
                    return null;
                }
            };
            taskPostalCode.setOnSucceeded(taskFinishEvent -> hideLoading());
            new Thread(taskPostalCode).start();
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
            var c = customerService.save(customer);
            if (c != null) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 2);
                Router.goTo(HomeController.class, c); //TODO create destine page
            } else {
                System.out.println("Não foi possivel cadastrar. Verificar dados. Talvez CPF/CNPJ já cadastrados"); //TODO create a pop-up
            }
        } catch (ConnectionFailureException e) {
            System.err.println("Error using customer service");
        }
    }

    public void showLoading() {
        postalCodeLoading.setVisible(true);
        neighborhoodLoading.setVisible(true);
        cityLoading .setVisible(true);
        stateLoading.setVisible(true);
        countryLoading.setVisible(true);
    }

    public void hideLoading() {
        postalCodeLoading.setVisible(false);
        neighborhoodLoading.setVisible(false);
        cityLoading .setVisible(false);
        stateLoading.setVisible(false);
        countryLoading.setVisible(false);
    }
}
