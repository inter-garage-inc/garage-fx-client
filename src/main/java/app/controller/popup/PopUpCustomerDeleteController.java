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
public class PopUpCustomerDeleteController {
    private Customer customer;

<<<<<<< HEAD:src/main/java/app/controller/popup/CustomerDeleteController.java
    private CustomersService customersService;
=======
    private CustomersService service;
>>>>>>> main:src/main/java/app/controller/popup/PopUpCustomerDeleteController.java

    public PopUpCustomerDeleteController() {
        customer = (Customer) Router.getUserData();
<<<<<<< HEAD:src/main/java/app/controller/popup/CustomerDeleteController.java
        customersService = new CustomersService();
=======
        service = new CustomersService();
>>>>>>> main:src/main/java/app/controller/popup/PopUpCustomerDeleteController.java
    }

    @FXML
    private void handleOnActionDelete(ActionEvent actionEvent) {
        try {
            var response = customersService.delete(customer.getId());
            if(response) {
                Router.showPopUp(PopUpDeleteSuccessController.class, 3);
                Router.goTo(CustomerSearchController.class);
            } else {
                Router.showPopUp(PopUpNotPossibleDeleteController.class, 2);
            }
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}
