package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpChangeSuccessfulController;
import app.data.Address;
import app.data.Customer;
import app.data.address.Country;
import app.data.address.State;
import app.router.RouteMapping;
import app.router.Router;
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
import javafx.scene.text.Text;

@RouteMapping(title = "Alterar customer")
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

    @FXML
    private MainMenuController menuController;

    private final Customer customer;

    private final CustomerService customerService;

    private final PostalCodeService postalCodeService;

    public CustomerChangeController() {
        customer = (Customer) Router.getUserData();
        customerService = new CustomerService();
        postalCodeService = new PostalCodeService();
    }

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

    public void handleTypedPostalCode(KeyEvent keyEvent) {
        if(fieldPostalCode.getPlainText().length() >= 8) {
            postalCodeLoading.setVisible(true);
            var task = new Task<Void>() {
                @Override
                protected Void call() {
                    try {
                        var address = postalCodeService.search(fieldPostalCode.getPlainText());
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

                @Override
                protected void succeeded() {
                    super.succeeded();
                    postalCodeLoading.setVisible(false);
                }
            };
            new Thread(task).start();
        }
    }

    public void handleSelectedCountry(ActionEvent actionEvent) {
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

    public void handleUpdate(ActionEvent actionEvent) {
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
            var c = customerService.update(customer.getId(), customerUpdated);
            if (c != null) {
                System.out.println(customer);
                System.out.println(customerUpdated);
                System.out.println(c);
                Router.showPopUp(PopUpChangeSuccessfulController.class, 3);
                Router.goTo(CustomerDetailsController.class, c, null); //TODO create destine page
            } else {
                System.out.println("Não foi possivel atualizar"); //TODO create a pop-up
            }
        } catch (ConnectionFailureException e) {
            System.err.println("Error using customer service");
        }
    }
}