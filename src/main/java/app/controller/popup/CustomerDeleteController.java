package app.controller.popup;

import app.client.ConnectionFailureException;
import app.controller.CustomerSearchController;
import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@RouteMapping(title = "Exclusão de Cliente", popup = true)
public class CustomerDeleteController {
    private Customer customer;

    private CustomersService service;

    public CustomerDeleteController() {
        customer = (Customer) Router.getUserData();
        service = new CustomersService();
    }

    @FXML
    private void handleOnActionDelete(ActionEvent actionEvent) {
        try {
            var response = service.delete(customer.getId());
            if(response) {
                Router.showPopUp(PopUpDeleteSuccessController.class, 3);
                Router.goTo(CustomerSearchController.class);
            } else {
//                Router.showPopUp(); TODO popup error trying to delete customer
                System.out.println("Erro ao deletar cliente");
            }
        } catch (ConnectionFailureException exception) {
//                Router.showPopUp(); TODO popup error trying call server
            System.out.println("Erro ao contatar servidor para deletar cliente");
        }
    }
}
