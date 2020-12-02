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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

@RouteMapping(title = "Cadastrar Novo Cliente")
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
    private TextField fieldCity;

    @FXML
    private ComboBox<State> comboBoxState;

    @FXML
    private ComboBox<Country> comboBoxCountry;

    @FXML
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

    @FXML
    private void handleOnActionRadioButtonCpf(ActionEvent actionEvent) {
        fieldCpfCnpj.setMask("###.###.###-##");
    }

    @FXML
    private void handleOnActionRadioButtonCnpj(ActionEvent actionEvent) {
        fieldCpfCnpj.setMask("##.###.###/####-##");
    }

    @FXML
    private void handleOnActionComboBoxCountry(ActionEvent actionEvent) {
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

    @FXML
    private void handleOnKeyReleasedFieldPostalCode(KeyEvent keyEvent) {
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
            var c = customersService.save(customer);
            if (c != null) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 3);
                Router.goTo(CustomerDetailsController.class, c); //TODO create destine page
            } else {
                System.out.println("Não foi possivel cadastrar. Verificar dados. Talvez CPF/CNPJ já cadastrados"); //TODO create a pop-up
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}