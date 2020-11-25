package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.CustomerNotFoundController;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomerService;
import app.util.MaskedTextField.MaskedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

@RouteMapping(title = "Buscar Cliente")
public class CustomerSearchController {

    @FXML
    private MainMenuController menuController;

    @FXML
    private MaskedTextField fieldCpfCnpj;

    @FXML
    private ImageView findLoading;

    private CustomerService service;

    public CustomerSearchController() {
        service = new CustomerService();
    }

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
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
    private void handleOnActionSearch() {
        try {
            var customer = service.findByCpfCnpj(fieldCpfCnpj.getPlainText());
            if(customer != null) {
                Router.goTo(CustomerDetailsController.class, customer, true);
            } else {
                Router.showPopUp(CustomerNotFoundController.class, 3);
            }
        } catch (ConnectionFailureException e) {
//            Router.showPopUp(); TODO popup error trying call server
            System.out.println("Erro ao contatar servidor para buscar cliente");
        }
    }

    @FXML
    private void handleOnActionButtonRegister(ActionEvent actionEvent) {
        Router.goTo(CustomerRegisterController.class, true);
    }
}