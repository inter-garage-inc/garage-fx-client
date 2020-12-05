package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpCustomerNotFoundController;
import app.controller.popup.PopUpServerCloseController;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;
import app.util.MaskedTextField.MaskedTextField;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Buscar Cliente")
public class CustomerSearchController {

    @FXML
    private MainMenuController menuController;

    @FXML
    private MaskedTextField fieldCpfCnpj;

    @FXML
    private ImageView findLoading;

    private CustomersService service;

    public CustomerSearchController() {
        service = new CustomersService();
    }

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }

    /**
     * This method add mask in the text field from CPF
     */
    public void handleOnActionRadioButtonCpf() {
        fieldCpfCnpj.setMask("###.###.###-##");
    }

    /**
     * This method add mask in the text field from CNPJ
     */
    public void handleOnActionRadioButtonCnpj() {
        fieldCpfCnpj.setMask("##.###.###/####-##");
    }

    /**
     * This method use the service {@link CustomersService} to search CPF or CNPJ
     */
    public void handleOnActionSearch() {
        try {
            var customer = service.findByCpfCnpj(fieldCpfCnpj.getPlainText());

            if(customer != null) {
                Router.goTo(CustomerDetailsController.class, customer, true);
            } else {
                Router.showPopUp(PopUpCustomerNotFoundController.class);
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    /**
     * This method call {@link CustomerRegisterController} using {@link Router}
     */
    public void handleOnActionButtonRegister() {
        Router.goTo(CustomerRegisterController.class, true);
    }
}